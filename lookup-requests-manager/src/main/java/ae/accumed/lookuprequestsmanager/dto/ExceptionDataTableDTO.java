package ae.accumed.lookuprequestsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDataTableDTO {

    private int draw;
    private int recordsTotal;
    private int recordsFiltered;
    private ArrayList<ExceptionDTO> data;
}
