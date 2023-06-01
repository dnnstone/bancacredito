package proyecto.bootcamp.banca.credito.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InputCreditClientDTO {
    private String clientId;
    private Double creditLimit;

}
