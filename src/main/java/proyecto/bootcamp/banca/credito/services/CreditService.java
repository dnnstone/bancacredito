package proyecto.bootcamp.banca.credito.services;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import proyecto.bootcamp.banca.credito.model.ClientCredit;
import proyecto.bootcamp.banca.credito.model.Movement;
import proyecto.bootcamp.banca.credito.repository.ClientCreditRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

@AllArgsConstructor
@Service
public class CreditService {
    private final ClientCreditRepository clientCreditRepository;
    private final MongoTemplate mongoTemplate;

    public List<ClientCredit> getAllClientCredit(){
        return clientCreditRepository.findAll();
    }

    public ClientCredit getClientCredit(String nCredit){
        Query query =new Query();
        query.addCriteria(Criteria.where("nCredit").is(nCredit));
        return mongoTemplate.findOne(query, ClientCredit.class);
    }

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
