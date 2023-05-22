package proyecto.bootcamp.banca.credito;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import proyecto.bootcamp.banca.credito.model.*;
import proyecto.bootcamp.banca.credito.repository.*;

import java.util.ArrayList;


@SpringBootApplication
public class CreditoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditoApplication.class, args);
	}
//	@Bean
//	CommandLineRunner runner(AccountTypeRepository accountRepository
//			, ClientTypeRepository repository
//			, ClientRepository clientRepository
//			, ClientAccountRepository clienteAccountRepository
//			, ClientCreditRepository clientCreditRepository){
//		return args->{
////
//////			Exclusivos de Clientes
////			AccountConditions personal = new AccountConditions(2,1,1,1,true);
////			AccountConditions empresarial= new AccountConditions(-1,0,-1,0,false);
////			CreditConditions cpersonal= new CreditConditions(1);
////			CreditConditions cempresarial= new CreditConditions(-1);
////
////			ClientType clientTypePersonal= new ClientType("Personal",personal,cpersonal);
////			ClientType clientTypeEmpresarial= new ClientType("Empresarial",empresarial,cempresarial);
////			repository.insert(clientTypePersonal);
////			repository.insert(clientTypeEmpresarial);
////
////			Client clientePersonal=new Client("12121212","DNI","Victor Yeray",clientTypePersonal);
////			Client clienteEmpresarial=new Client("201989898989","RUC","Silvy Company",clientTypeEmpresarial);
////			clientRepository.insert(clientePersonal);
////			clientRepository.insert(clienteEmpresarial);
////
//////			Exclusivos para cuenta
////			Conditions ahorros= new Conditions(0,30,0,"-","-");
////			Conditions corriente= new Conditions(10,-1,0,"1+","0+");
////			Conditions pfijo= new Conditions(0,1,16,"-","-");
////
////			AccountType cAhorros= new AccountType("Ahorros",ahorros);
////			AccountType cCorriente= new AccountType("Corriente",corriente);
////			AccountType cPFijo= new AccountType("Plazo Fijo",pfijo);
////
////			accountRepository.insert(cAhorros);
////			accountRepository.insert(cCorriente);
////			accountRepository.insert(cPFijo);
////
////			ClientAccount ca1 = new ClientAccount("0011-121232-000232123",cAhorros,clientePersonal,new ArrayList<Movement>(),0.0);
////			ClientAccount ca2 = new ClientAccount("0011-989898-000232123",cCorriente,clienteEmpresarial,new ArrayList<Movement>(),0.0);
////			ClientAccount ca3 = new ClientAccount("0011-121232-000333333",cPFijo,clientePersonal,new ArrayList<Movement>(),0.0);
////			clienteAccountRepository.insert(ca1);
////			clienteAccountRepository.insert(ca2);
////			clienteAccountRepository.insert(ca3);
////
////			//Exclusivo para Credito
////			CardCredit ccPersona= new CardCredit("12121212","Personal");
////			CardCredit ccEmpresearial= new CardCredit("98989898","Empresarial");
////			ClientCredit cc1= new ClientCredit("0022-11111-222222",clientePersonal,ccPersona,new ArrayList<>(),3000.00);
////			ClientCredit cc2= new ClientCredit("0022-99999-888888",clienteEmpresarial,ccPersona,new ArrayList<>(),10000.00);
////			clientCreditRepository.insert(cc1);
////			clientCreditRepository.insert(cc2);
////			System.out.println("ejecuté repositorio credito");
//		};
//	}

}
