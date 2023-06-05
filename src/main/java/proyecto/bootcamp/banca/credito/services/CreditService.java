package proyecto.bootcamp.banca.credito.services;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import proyecto.bootcamp.banca.credito.DTO.InputCreditClientDTO;
import proyecto.bootcamp.banca.credito.model.ClientCredit;

public interface CreditService {
    public Flowable<ClientCredit> getAllClientCredit();
    public Flowable<ClientCredit> getAllClientAccountByDoc(String nDoc);
    public Maybe<ClientCredit> getClientCredit(String nCredit);
    public Maybe<ClientCredit> addPay(String nCredit, Double amount);
    public Maybe<ClientCredit> addCharge(String nCredit, Double amount);
    public Maybe<ClientCredit> createClientCredit(InputCreditClientDTO inputCreditClientDTO);
    public Maybe<Void> deleteClientCreditByDoc(String ndoc);

}
