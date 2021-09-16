package ae.accumed.lookuprequestsmanager.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayerStatisticsDTO {
    String name;
    String pendingCount;
    String eligibleCount;
    String notEligibleCount;
    String errorCount;
    long totalCount;
}
