package proyecto.bootcamp.banca.credito.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import proyecto.bootcamp.banca.credito.model.Client;

public interface ClientRepository extends ReactiveMongoRepository<Client, String> {
}
