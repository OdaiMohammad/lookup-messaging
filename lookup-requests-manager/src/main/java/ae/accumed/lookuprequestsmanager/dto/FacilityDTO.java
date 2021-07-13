package ae.accumed.lookuprequestsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDTO {
    private int id;

    private String description;

    @NotNull(message = "Please enter a code for this facility")
    @NotEmpty(message = "Please enter a code for this facility")
    private String facilityCode;

    @NotNull(message = "Please enter a name for this facility")
    @NotEmpty(message = "Please enter a name for this facility")
    private String facilityName;
}
