package proyecto.bootcamp.banca.credito.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import proyecto.bootcamp.banca.credito.model.ClientType;

public interface ClientTypeRepository
        extends MongoRepository<ClientType,String> {

}
