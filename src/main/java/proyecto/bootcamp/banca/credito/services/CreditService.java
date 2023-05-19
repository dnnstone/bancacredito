package proyecto.bootcamp.banca.credito.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilderFactory;
import proyecto.bootcamp.banca.credito.bean.ApiCliente;
import proyecto.bootcamp.banca.credito.model.Client;
import proyecto.bootcamp.banca.credito.model.ClientCredit;
import proyecto.bootcamp.banca.credito.model.ClientDto;
import proyecto.bootcamp.banca.credito.model.Movement;
import proyecto.bootcamp.banca.credito.repository.ClientCreditRepository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

@AllArgsConstructor
@Service
public class CreditService {
    private final ClientCreditRepository clientCreditRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    private Environment env;



    public List<ClientCredit> getAllClientCredit(){
        return clientCreditRepository.findAll();
    }

//Probamos el micro de Clientes
    public Client testMicroservicio(){
        RestTemplate restTemplate= new RestTemplate();
        String uri= env.getProperty("apis.cliente")+"6466d4b7d90ed775c7863f90";
        Client cli= restTemplate.getForObject(uri, Client.class);
        System.out.println("cli: "+ cli);

        return cli;
    }

//    Obtenemos el Credito del Cliente con su respectivo nCredit
    public ClientCredit getClientCredit(String nCredit) {


        Query query =new Query();
        query.addCriteria(Criteria.where("nCredit").is(nCredit));
        ClientCredit clientCredit= mongoTemplate.findOne(query, ClientCredit.class);

        System.out.println("api "+env.getProperty("apis.cliente")+clientCredit.getClient().getId());
        String uri= env.getProperty("apis.cliente")+clientCredit.getClient().getId();
        RestTemplate restTemplate= new RestTemplate();


        Client client= restTemplate.getForObject(uri, Client.class);
        clientCredit.setClient(client);

        return clientCredit;

    }
//    Agrega un movimiento de pago del Credito del cliente
    public Boolean addPay(String nCredit, Double amount){
        ClientCredit clientCredit= getClientCredit(nCredit);
        Supplier<Boolean> suMov=()->{
            Movement addMoviment= new Movement("pago",amount,new Date());
            List<Movement> listMovements= clientCredit.getMovements()!=null?clientCredit.getMovements():new ArrayList<>();
            listMovements.add(addMoviment);
            clientCredit.setMovements(listMovements);
            clientCredit.setLimitCredit(clientCredit.getLimitCredit()+amount);
            clientCreditRepository.save(clientCredit);
            return true;
        };
        return suMov.get();
    }
//    Agrega un movimiento de Carga del Credito del cliente
    public Boolean addCharge(String nCredit, Double amount){
        ClientCredit clientCredit= getClientCredit(nCredit);
        BiPredicate<Double,Double> biOutdraw= (s, m)->s.compareTo(m)<0;
        Supplier<Boolean> suMov=()->{
            if(biOutdraw.test(clientCredit.getLimitCredit(),amount)){
                return false;
            }
            else{
                Movement addMoviment= new Movement("carga",amount,new Date());
                List<Movement> listMovements= clientCredit.getMovements()!=null?clientCredit.getMovements():new ArrayList<>();
                listMovements.add(addMoviment);
                clientCredit.setMovements(listMovements);
                clientCredit.setLimitCredit(clientCredit.getLimitCredit()-amount);
                clientCreditRepository.save(clientCredit);
                return true;
            }
        };
        return suMov.get();
    }

}
