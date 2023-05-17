package proyecto.bootcamp.banca.credito.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import proyecto.bootcamp.banca.credito.model.Client;

public interface ClientRepository extends MongoRepository <Client, String>{
}
