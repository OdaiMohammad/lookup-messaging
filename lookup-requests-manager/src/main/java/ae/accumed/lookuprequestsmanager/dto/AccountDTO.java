package ae.accumed.lookuprequestsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private int id;
    private Boolean isActive;
    private String userName;
    private FacilityDTO facility;
    private PayerDTO payer;
}
