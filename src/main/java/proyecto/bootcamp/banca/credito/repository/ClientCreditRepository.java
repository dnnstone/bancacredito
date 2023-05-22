package proyecto.bootcamp.banca.credito.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import proyecto.bootcamp.banca.credito.model.ClientCredit;

public interface ClientCreditRepository  extends ReactiveMongoRepository<ClientCredit,String> {
}
