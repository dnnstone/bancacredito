package proyecto.bootcamp.banca.credito.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import proyecto.bootcamp.banca.credito.model.ClientCredit;

public interface ClientCreditRepository  extends MongoRepository<ClientCredit,String> {
}
