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

    private Integer bulkId;
    private String createDate;
    private String eid;
    private String result;
    private String resultDate;
    private String source;
    private String status;
    private String html;
}
