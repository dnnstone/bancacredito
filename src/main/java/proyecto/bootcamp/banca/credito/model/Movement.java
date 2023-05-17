package proyecto.bootcamp.banca.credito.model;

import lombok.Data;

import java.util.Date;

@Data
public class Movement {
    String type ;
    Double amount;
    Date date;

    public Movement(String type, Double amount, Date date) {
        this.type = type;
        this.amount = amount;
        this.date = date;
    }
}
