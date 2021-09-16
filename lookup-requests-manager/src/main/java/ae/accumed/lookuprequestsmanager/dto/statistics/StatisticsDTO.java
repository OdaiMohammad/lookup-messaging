package ae.accumed.lookuprequestsmanager.dto.statistics;

import lombok.Data;

import java.util.List;

@Data
public class StatisticsDTO {
    List<PayerStatisticsDTO> payerStats;
}
