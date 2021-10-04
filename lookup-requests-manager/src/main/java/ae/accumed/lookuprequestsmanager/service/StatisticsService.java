package ae.accumed.lookuprequestsmanager.service;

import ae.accumed.lookuprequestsmanager.dto.statistics.*;
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
        statisticsDTO.setAccountStats(getAccountStatistics(start, end));
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
            long pendingRequestsCount = statsForPayer.stream().filter(dto -> dto.getStatus().equalsIgnoreCase("pending")).findAny().orElseGet(() -> new RequestsPerPayerDTO(payer, "pending", 0)).getRequestsCount();
            long eligibleRequestsCount = statsForPayer.stream().filter(dto -> dto.getStatus().equalsIgnoreCase("eligible")).findAny().orElseGet(() -> new RequestsPerPayerDTO(payer, "eligible", 0)).getRequestsCount();
            long nonEligibleRequestsCount = statsForPayer.stream().filter(dto -> dto.getStatus().equalsIgnoreCase("not_eligible")).findAny().orElseGet(() -> new RequestsPerPayerDTO(payer, "not_eligible", 0)).getRequestsCount();
            long errorRequestsCount = statsForPayer.stream().filter(dto -> dto.getStatus().equalsIgnoreCase("error")).findAny().orElseGet(() -> new RequestsPerPayerDTO(payer, "error", 0)).getRequestsCount();
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

    private List<AccountStatisticsDTO> getAccountStatistics(Date start, Date end) {
        List<RequestsPerAccountDTO> requestsPerAccountDTOList = transactionRepository.getRequestsPerAccountStatistics(start, end);
        List<AccountStatisticsDTO> accountStatisticsDTOS = new ArrayList<>();
        Set<Integer> accountIds = new HashSet<>();
        for (RequestsPerAccountDTO requestsPerAccountDTO : requestsPerAccountDTOList) {
            accountIds.add(requestsPerAccountDTO.getId());
        }
        for (Integer accountId : accountIds) {
            List<RequestsPerAccountDTO> statsForAccount = requestsPerAccountDTOList.stream().filter(dto -> dto.getId() == accountId).collect(Collectors.toList());
            long pendingRequestsCount = statsForAccount.stream().filter(dto -> dto.getStatus().equalsIgnoreCase("pending")).findAny().orElseGet(() -> new RequestsPerAccountDTO(accountId, "pending", 0)).getRequestsCount();
            long eligibleRequestsCount = statsForAccount.stream().filter(dto -> dto.getStatus().equalsIgnoreCase("eligible")).findAny().orElseGet(() -> new RequestsPerAccountDTO(accountId, "eligible", 0)).getRequestsCount();
            long nonEligibleRequestsCount = statsForAccount.stream().filter(dto -> dto.getStatus().equalsIgnoreCase("not_eligible")).findAny().orElseGet(() -> new RequestsPerAccountDTO(accountId, "not_eligible", 0)).getRequestsCount();
            long errorRequestsCount = statsForAccount.stream().filter(dto -> dto.getStatus().equalsIgnoreCase("error")).findAny().orElseGet(() -> new RequestsPerAccountDTO(accountId, "error", 0)).getRequestsCount();
            long totalRequestsCount = statsForAccount.stream().mapToLong(RequestsPerAccountDTO::getRequestsCount).sum();
            accountStatisticsDTOS.add(
                    new AccountStatisticsDTO(
                            accountId,
                            String.format("%s%% (%d)", calculatePercentage(pendingRequestsCount, totalRequestsCount), pendingRequestsCount),
                            String.format("%s%% (%d)", calculatePercentage(eligibleRequestsCount, totalRequestsCount), eligibleRequestsCount),
                            String.format("%s%% (%d)", calculatePercentage(nonEligibleRequestsCount, totalRequestsCount), nonEligibleRequestsCount),
                            String.format("%s%% (%d)", calculatePercentage(errorRequestsCount, totalRequestsCount), errorRequestsCount),
                            totalRequestsCount
                    )
            );
        }
        return accountStatisticsDTOS;
    }

    private String calculatePercentage(double obtained, double total) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(obtained * 100 / total);
    }
}
