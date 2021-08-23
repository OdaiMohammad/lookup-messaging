package ae.accumed.lookuprequestsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailsDTO {
    private int id;
    private int accountId;

    private String bulkId;
    private String createResultDate;
    private String eid;
    private String result;
    private String source;
    private String status;
    private String html;
    private String patientType;
    private String processTime;
    private String errorMessage;
    private String userId;
}
