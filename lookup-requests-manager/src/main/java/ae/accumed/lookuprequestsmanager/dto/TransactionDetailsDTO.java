package ae.accumed.lookuprequestsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailsDTO {
    private int id;

    private String bulkId;
    private String createDate;
    private String eid;
    private String result;
    private String resultDate;
    private String source;
    private String status;
    private String html;
}
