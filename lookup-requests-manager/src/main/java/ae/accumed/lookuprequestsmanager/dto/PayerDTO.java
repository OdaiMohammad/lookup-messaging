package ae.accumed.lookuprequestsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayerDTO {
    private int id;
    private Boolean isActive;
    private String payerCode;
    private String payerName;
}
