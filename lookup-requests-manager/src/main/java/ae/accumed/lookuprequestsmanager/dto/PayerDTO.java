package ae.accumed.lookuprequestsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayerDTO {
    private int id;

    private Boolean isActive;

    @NotNull(message = "Please enter a code for this payer")
    @NotEmpty(message = "Please enter a code for this payer")
    private String payerCode;

    @NotNull(message = "Please enter a name for this payer")
    @NotEmpty(message = "Please enter a name for this payer")
    private String payerName;


    @NotNull(message = "Please enter the estimated crawling time for this payer")
    @NotEmpty(message = "Please enter the estimated crawling time for this payer")
    private String crawlerCountMs;
}
