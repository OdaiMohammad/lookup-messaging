package ae.accumed.lookuprequestsmanager.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class RequestsPerPayerDTO {
    String payerName;
    String status;
    long requestsCount;
}
