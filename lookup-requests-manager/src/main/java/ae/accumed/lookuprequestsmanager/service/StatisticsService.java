package ae.accumed.lookuprequestsmanager.service;

import ae.accumed.lookuprequestsmanager.dto.statistics.PayerStatisticsDTO;
import ae.accumed.lookuprequestsmanager.dto.statistics.RequestsPerPayerDTO;
import ae.accumed.lookuprequestsmanager.dto.statistics.StatisticsDTO;
import ae.accumed.lookuprequestsmanager.repository.TransactionRepository;
import ae.accumed.lookuprequestsmanager.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public StatisticsService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public StatisticsDTO getStats(Date start, Date end) {
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setPayerStats(getPayerStatistics(start, end));
        return statisticsDTO;
    }

    private List<PayerStatisticsDTO> getPayerStatistics(Date start, Date end) {
        List<RequestsPerPayerDTO> requestsPerPayerDTOList = transactionRepository.getRequestsPerPayerStatistics(start, end);
        List<PayerStatisticsDTO> payerStatisticsDTOs = new ArrayList<>();
        Set<String> payers = new HashSet<>();
        for (RequestsPerPayerDTO requestsPerPayerDTO : requestsPerPayerDTOList) {
            payers.add(requestsPerPayerDTO.getPayerName());
        }
        for (String payer : payers) {
            List<RequestsPerPayerDTO> statsForPayer = requestsPerPayerDTOList.stream().filter(dto -> dto.getPayerName().equals(payer)).collect(Collectors.toList());
            long pendingRequestsCount = statsForPayer.stream().filter(dto -> dto.getStatus().toLowerCase().equals("pending")).findAny().orElseGet(() -> new RequestsPerPayerDTO(payer, "pending", 0)).getRequestsCount();
            long eligibleRequestsCount = statsForPayer.stream().filter(dto -> dto.getStatus().toLowerCase().equals("eligible")).findAny().orElseGet(() -> new RequestsPerPayerDTO(payer, "eligible", 0)).getRequestsCount();
            long nonEligibleRequestsCount = statsForPayer.stream().filter(dto -> dto.getStatus().toLowerCase().equals("not_eligible")).findAny().orElseGet(() -> new RequestsPerPayerDTO(payer, "not_eligible", 0)).getRequestsCount();
            long errorRequestsCount = statsForPayer.stream().filter(dto -> dto.getStatus().toLowerCase().equals("error")).findAny().orElseGet(() -> new RequestsPerPayerDTO(payer, "error", 0)).getRequestsCount();
            long totalRequestsCount = statsForPayer.stream().mapToLong(RequestsPerPayerDTO::getRequestsCount).sum();
            payerStatisticsDTOs.add(
                    new PayerStatisticsDTO(
                            payer,
                            String.format("%s%% (%d)", calculatePercentage(pendingRequestsCount, totalRequestsCount), pendingRequestsCount),
                            String.format("%s%% (%d)", calculatePercentage(eligibleRequestsCount, totalRequestsCount), eligibleRequestsCount),
                            String.format("%s%% (%d)", calculatePercentage(nonEligibleRequestsCount, totalRequestsCount), nonEligibleRequestsCount),
                            String.format("%s%% (%d)", calculatePercentage(errorRequestsCount, totalRequestsCount), errorRequestsCount),
                            totalRequestsCount
                    )
            );
        }
        return payerStatisticsDTOs;
    }

    private String calculatePercentage(double obtained, double total) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(obtained * 100 / total);
    }
}
