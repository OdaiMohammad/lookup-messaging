package ae.accumed.lookuprequestsmanager.dto;

import ae.accumed.lookuprequestsmanager.validation.PasswordMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatch(message = "Passwords do not match")
public class CreateAccountDTO {
    private int id;

    @Min(message = "Please select a payer", value = 1)
    private int payerId;

    @Min(message = "Please select a facility", value = 1)
    private int facilityId;

    @NotNull(message = "Please enter a username for this account")
    @NotEmpty(message = "Please enter a username for this account")
    private String userName;

    private String password;

    private String repeatPassword;

    private Boolean isActive;
}
