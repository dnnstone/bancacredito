package proyecto.bootcamp.banca.credito.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class AccountConditions {
    private Integer nMaxCuentas;
    private Integer nMaxCAhorro;
    private Integer nMaxCCorriente;
    private Integer nMaxCPFijo;
    private Boolean orCCCPF;

//    public AccountConditions(Integer nMaxCuentas, Integer nMaxCAhorro, Integer nMaxCCorriente, Integer nMaxCPFijo, Boolean orCCCPF) {
//        this.nMaxCuentas = nMaxCuentas;
//        this.nMaxCAhorro = nMaxCAhorro;
//        this.nMaxCCorriente = nMaxCCorriente;
//        this.nMaxCPFijo = nMaxCPFijo;
//        this.orCCCPF = orCCCPF;
//    }
}
