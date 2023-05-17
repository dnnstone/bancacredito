package proyecto.bootcamp.banca.credito.model;

import lombok.Data;

@Data
public class CardCredit {
    private String nCarCredit;
    private String tipo;

    public CardCredit(String nCarCredit, String tipo) {
        this.nCarCredit = nCarCredit;
        this.tipo = tipo;
    }
}
