package proyecto.bootcamp.banca.credito.services;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import proyecto.bootcamp.banca.credito.DTO.InputCreditClientDTO;
import proyecto.bootcamp.banca.credito.model.*;
import proyecto.bootcamp.banca.credito.repository.ClientCreditRepository;
import proyecto.bootcamp.banca.credito.repository.ClientRepository;
import proyecto.bootcamp.banca.credito.utils.ClientCreditUtils;
import reactor.adapter.rxjava.RxJava3Adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CreditServiceImp implements CreditService{
    @Autowired
    private ClientCreditRepository clientCreditRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    private Logger logger = LoggerFactory.getLogger(CreditService.class);
    @Autowired
    private Environment env;

    public Flowable<ClientCredit> getAllClientCredit(){
        return clientCreditRepository.findAll()
                .as(RxJava3Adapter::fluxToFlowable);
    }
    public Flowable<ClientCredit> getAllClientAccountByDoc(String nDoc){
        return clientCreditRepository.findAll()
                .filter(s->s.getClient().getNDoc().equals(nDoc))
                .as(RxJava3Adapter::fluxToFlowable);
    }

    //    Obtenemos el Credito del Cliente con su respectivo nCredit
    public Maybe<ClientCredit> getClientCredit(String nCredit) {
        Query query =new Query();
        query.addCriteria(Criteria.where("nCredit").is(nCredit));
        return mongoTemplate.
                findOne(query, ClientCredit.class)
                .as(RxJava3Adapter::monoToMaybe)
                .map(client->{
                    RestTemplate restTemplate= new RestTemplate();
                    client.setClient(restTemplate.getForObject(env.getProperty("apis.cliente")+client.getClient().getId(), Client.class));
                    return client;
                });
    }
    //    Agrega un movimiento de pago del Credito del cliente
    public Maybe<ClientCredit> addPay(String nCredit, Double amount){

        return this.getClientCredit(nCredit)
                .map(cl->{
                    cl.setSaldo(cl.getSaldo()+amount);
                    List<Movement> listMov=cl.getMovements();
                    listMov.add(new Movement("pago",amount,new Date()));
                    cl.setMovements(listMov);
                    return cl;})
                .to(RxJava3Adapter::maybeToMono)
                .flatMap(clientCreditRepository::save)
                .as(RxJava3Adapter::monoToMaybe);

    }
    //    Agrega un movimiento de Carga del Credito del cliente
    public Maybe<ClientCredit> addCharge(String nCredit, Double amount){
//        ClientCredit clientCredit= getClientCredit(nCredit);
//        BiPredicate<Double,Double> biOutdraw= (s, m)->s.compareTo(m)<0;
//        Supplier<Boolean> suMov=()->{
//            if(biOutdraw.test(clientCredit.getLimitCredit(),amount)){
//                return false;
//            }
//            else{
//                Movement addMoviment= new Movement("carga",amount,new Date());
//                List<Movement> listMovements= clientCredit.getMovements()!=null?clientCredit.getMovements():new ArrayList<>();
//                listMovements.add(addMoviment);
//                clientCredit.setMovements(listMovements);
//                clientCredit.setLimitCredit(clientCredit.getLimitCredit()-amount);
//                clientCreditRepository.save(clientCredit);
//                return true;
//            }
//        };
//        return suMov.get();
        return this.getClientCredit(nCredit)
                .filter(cl->cl.getSaldo()>=amount)
                .map(cl->{
                    cl.setSaldo(cl.getSaldo()-amount);
                    List<Movement> listMov=cl.getMovements();
                    listMov.add(new Movement("carga",amount,new Date()));
                    cl.setMovements(listMov);
                    return cl;})
                .to(RxJava3Adapter::maybeToMono)
                .flatMap(clientCreditRepository::save)
                .as(RxJava3Adapter::monoToMaybe);
    }
    //    Crea un nuevo cliente de Credito
    public Maybe<ClientCredit> createClientCredit(InputCreditClientDTO inputCreditClientDTO){

        return Maybe.just(inputCreditClientDTO)
                .filter(this::isAllowedNewCredit)
                .map(s-> {
                     Client clientTemp= clientRepository.findById(s.getClientId()).block();
                    return new ClientCredit("0999-"+ ClientCreditUtils.createNumber(),
                            clientTemp,
                            new CardCredit(ClientCreditUtils.createNumber(),
                                            clientTemp.getTypeClient().getName()
                                            ),
                            new ArrayList<>(),
                            s.getCreditLimit()
                            );
                })
                .to(RxJava3Adapter::maybeToMono)
                .flatMap(clientCreditRepository::insert)
                .as(RxJava3Adapter::monoToMaybe);
    }

    // Este metodo evalua si es posible añadir un nuevo credito al cliente
    private Boolean isAllowedNewCredit(InputCreditClientDTO inputCreditClientDTO){
        Client clientTemp=clientRepository.findById(inputCreditClientDTO.getClientId()).block();
        CreditConditions conditions= clientTemp.getTypeClient().getCreditConditions();

        return conditions.getMaxCredits().equals(-1)
                || (this.getAllClientAccountByDoc(clientTemp.getNDoc()).count().blockingGet()<conditions.getMaxCredits())
                && this.hasDebt(clientTemp.getNDoc()).blockingGet(); //añadiendo validacion de si tiene deudas, no podrá tener ningun producto
    }
    //    Borra un ClienteCredit
    public Maybe<Void> deleteClientCreditByDoc(String ndoc){
        return clientCreditRepository.deleteAll(this.getAllClientAccountByDoc(ndoc)).as(RxJava3Adapter::monoToMaybe);
    }

    public Single<Boolean> hasDebt(String ndoc){
        return this.getAllClientAccountByDoc(ndoc)
                .map(s->{logger.info("info INICIAL clientcredit saldo: {} limite {}",s.getSaldo(),s.getLimitCredit()); return s;})
                .filter(s->s.getSaldo().equals(s.getLimitCredit()) )
                .firstElement()
                .map(s->{
                    logger.info("info clientcredit saldo: {} limite {}",s.getSaldo(),s.getLimitCredit());
                    return true;})
                .defaultIfEmpty(this.hasAccount(ndoc));
    }

    private Boolean hasAccount(String ndoc){
        return this.getAllClientAccountByDoc(ndoc).count().blockingGet().equals(0L)?true:false;
    }
}
