package ae.accumed.lookuprequestsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountDTO {
    private int id;
    private int payerId;
    private int facilityId;
    private Boolean isActive;
    private String userName;
    private String password;
    private String repeatPassword;
}
