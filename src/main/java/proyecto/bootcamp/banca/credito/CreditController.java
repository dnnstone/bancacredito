package proyecto.bootcamp.banca.credito;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import proyecto.bootcamp.banca.credito.model.Client;
import proyecto.bootcamp.banca.credito.model.ClientCredit;
import proyecto.bootcamp.banca.credito.services.CreditService;

import java.util.List;

@RestController
@RequestMapping("api/v1/clientcredit")
@AllArgsConstructor
public class CreditController {
    private final CreditService creditService;

    @GetMapping()
    public Flowable<ClientCredit> fetchAllClientCredit(){

        return creditService.getAllClientCredit();
    }
    @GetMapping("/client/ndoc/{nDoc}")
    public Flowable<ClientCredit> getAllClientAccountbyClientDoc(@PathVariable("nDoc") String nDoc){

        return creditService.getAllClientAccountByDoc(nDoc);
    }
    @GetMapping("/{nCredit}")
    public Maybe<ClientCredit> fetchClientCredit(@PathVariable("nCredit") String nCredit){

        return creditService.getClientCredit(nCredit);
    }

    @PostMapping("/pay")
    public Maybe<ClientCredit> payClientCredit(@RequestParam("nCredit") String nCredit ,
                                                @RequestParam("monto") Double amount){
        return creditService.addPay(nCredit, amount);
    }
    @PostMapping("/charge")
    public Maybe<ClientCredit>  chargeClientCredit( @RequestParam("nCredit") String nCredit ,
                                             @RequestParam("monto") Double amount){
        return creditService.addCharge(nCredit, amount);
    }
    @GetMapping("/testMs")
    public Client test(){
        return creditService.testMicroservicio();
    }
}
