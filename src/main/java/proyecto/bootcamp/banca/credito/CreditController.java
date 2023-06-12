package proyecto.bootcamp.banca.credito;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.bootcamp.banca.credito.DTO.InputCreditClientDTO;
import proyecto.bootcamp.banca.credito.model.Client;
import proyecto.bootcamp.banca.credito.model.ClientCredit;
import proyecto.bootcamp.banca.credito.services.CreditService;

import java.util.List;

@RestController
@RequestMapping("api/v1/clientcredit")
public class CreditController {
    @Autowired
    private CreditService creditService;

    // retorna todos los creditos
    @GetMapping()
    public Single<ResponseEntity<Flowable<ClientCredit>>> fetchAllClientCredit(){

        return Single.just(ResponseEntity.ok().body(creditService.getAllClientCredit()));
    }

    //retorna creditos por nDocumento del cliente
    @GetMapping("/client/ndoc/{nDoc}")
    public Single<ResponseEntity<Flowable<ClientCredit>>> getAllClientAccountbyClientDoc(@PathVariable("nDoc") String nDoc){

        return Single.just(ResponseEntity.ok().body(creditService.getAllClientAccountByDoc(nDoc)));
    }

   //retorna credito por numero de credito
    @GetMapping("/{nCredit}")
    public Single<ResponseEntity<ClientCredit>> fetchClientCredit(@PathVariable("nCredit") String nCredit){

        return creditService.getClientCredit(nCredit).map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //realiza un pago al credito
    @PostMapping("/pay")
    public Single<ResponseEntity<ClientCredit>> payClientCredit(@RequestParam("nCredit") String nCredit ,
                                                @RequestParam("monto") Double amount){
        return creditService.addPay(nCredit, amount).map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    // realiza una carga al credito
    @PostMapping("/charge")
    public Single<ResponseEntity<ClientCredit>>  chargeClientCredit( @RequestParam("nCredit") String nCredit ,
                                             @RequestParam("monto") Double amount){
        return creditService.addCharge(nCredit, amount).map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //crea un nuevo Credito a un cliente (ClientAccount)
    @PostMapping("/create")
    public Maybe<ClientCredit> saveClientCredit(@RequestBody InputCreditClientDTO inputCreditClientDTO){
        return creditService.createClientCredit(inputCreditClientDTO);
    }

    //borra un clienteCredito por ndoc
    @DeleteMapping("/delete/{ndoc}")
    public Maybe<Void> deleteClientCredit(@PathVariable("ndoc") String ndoc){
        return creditService.deleteClientCreditByDoc(ndoc);
    }

    @GetMapping("/hasDebt/{ndoc}")
    public Single<ResponseEntity<Boolean>> hasDebt(@PathVariable("ndoc") String ndoc){
        return creditService.hasDebt(ndoc).map(p -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(p));
    }
}
