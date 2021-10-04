package ae.accumed.lookuprequestsmanager.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestsPerAccountDTO {
    int id;
    String status;
    long requestsCount;
}
