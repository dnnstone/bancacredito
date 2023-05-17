package proyecto.bootcamp.banca.credito.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import proyecto.bootcamp.banca.credito.model.ClientAccount;

public interface ClientAccountRepository extends MongoRepository<ClientAccount,String> {
}
