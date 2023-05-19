package proyecto.bootcamp.banca.credito.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class ApiCliente {
    @Value("${apis.cliente}")
    private String apiCliente;


}
