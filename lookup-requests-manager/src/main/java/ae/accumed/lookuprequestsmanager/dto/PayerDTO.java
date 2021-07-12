package ae.accumed.lookuprequestsmanager.dto;

import lombok.Data;

@Data
public class PayerDTO {
    private final int id;
    private final Boolean isActive;
    private final String payerCode;
    private final String payerName;
}
