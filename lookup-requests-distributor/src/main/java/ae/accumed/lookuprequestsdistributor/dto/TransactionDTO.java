package ae.accumed.lookuprequestsdistributor.dto;

import lombok.Data;

@Data
public class TransactionDTO {
    private int id;
    private int account_id;
    private String bulk_id;
    private String eid;
    private String status;
    private String p_type;
}
