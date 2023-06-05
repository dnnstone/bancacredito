package proyecto.bootcamp.banca.credito;

import io.reactivex.rxjava3.core.Maybe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import proyecto.bootcamp.banca.credito.model.Client;
import proyecto.bootcamp.banca.credito.repository.ClientRepository;
import proyecto.bootcamp.banca.credito.services.CreditService;

@SpringBootTest
class CreditoApplicationTests {


	@Autowired
	private CreditService creditService;
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	@Autowired
	private Environment env;


	@ParameterizedTest
	@ValueSource(strings= {"43232343","20143232343"})
	void contextLoads(String ndoc) {
		System.out.println("ndoc: "+ndoc);
	}

//	@Test
	@DisplayName("Verificar Creacion de Creditos por cliente")
	@ParameterizedTest
	@ValueSource(strings= {"43232343","20143232343"})
	void testCreationCredit(String ndoc){
		//usaremos 2 clientes ya creados y sin ninguna cuenta ni credito
		//testearemos los requisitos para cada uno

		RestTemplate restTemplate= new RestTemplate();
		Client client=restTemplate.getForObject(env.getProperty("apis.cliente")+"doc/"+ndoc, Client.class);
		Assertions.assertNotNull(client);
	}

	@BeforeEach
	@ParameterizedTest
	@ValueSource(strings= {"43232343","20143232343"})
	void beforeAllTest(String ndoc){
//		Client clientPersonal= clientRepository.
		System.out.println("entro al beforeEach");
		RestTemplate restTemplate= new RestTemplate();
		Client client=restTemplate.getForObject(env.getProperty("apis.cliente")+"doc/"+ndoc, Client.class);
		Assertions.assertNotNull(client);
		creditService.deleteClientCreditByDoc(ndoc).blockingGet();


	}
}
