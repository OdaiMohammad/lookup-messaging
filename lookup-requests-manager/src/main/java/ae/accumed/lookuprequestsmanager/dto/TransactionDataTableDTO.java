package ae.accumed.lookuprequestsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDataTableDTO {

    private int draw;
    private int recordsTotal;
    private int recordsFiltered;
    private ArrayList<TransactionDTO> data;
}
