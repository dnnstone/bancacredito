package proyecto.bootcamp.banca.credito.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@Document
public class ClientCredit {
    @Id
    private String id;
    @Indexed(unique = true)
    private String nCredit;
    private Client client;
    private CardCredit carClient;
    private List<Movement> movements;
    private Double limitCredit;

    public ClientCredit(String nCredit, Client client, CardCredit carClient, List<Movement> movements, Double limitCredit) {
        this.nCredit = nCredit;
        this.client = client;
        this.carClient = carClient;
        this.movements = movements;
        this.limitCredit = limitCredit;
    }
}

