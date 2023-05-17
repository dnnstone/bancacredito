package proyecto.bootcamp.banca.credito.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import proyecto.bootcamp.banca.credito.model.AccountType;

public interface AccountTypeRepository extends MongoRepository<AccountType,String> {
}
