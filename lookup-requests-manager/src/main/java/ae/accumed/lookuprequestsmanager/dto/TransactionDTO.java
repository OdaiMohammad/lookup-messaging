package ae.accumed.lookuprequestsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private int id;
    private int accountId;

    private String bulkId;
    private String createResultDate;
    private String eid;
    private String source;
    private String status;
    private String processTime;
    private String payerName;
}
