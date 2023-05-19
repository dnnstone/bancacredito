package proyecto.bootcamp.banca.credito.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientType {
    @Id
    private String id;
    private String name;
    private AccountConditions accountConditions;
    private CreditConditions creditConditions;

//    public ClientType(String name, AccountConditions accountConditions, CreditConditions creditConditions) {
//        this.name = name;
//        this.accountConditions = accountConditions;
//        this.creditConditions = creditConditions;
//    }
}
