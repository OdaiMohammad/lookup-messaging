package ae.accumed.lookuprequestsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDTO {
    private int id;
    private String exceptionDate;
    private String exceptionTrace;
    private String exceptionMessage;
    private String emiratesId;
    private String facility;
}
