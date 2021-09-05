package ae.accumed.lookuprequestsmanager.service;

import ae.accumed.lookuprequestsmanager.dto.AccountDTO;
import ae.accumed.lookuprequestsmanager.dto.CreateAccountDTO;
import ae.accumed.lookuprequestsmanager.dto.FacilityDTO;
import ae.accumed.lookuprequestsmanager.dto.PayerDTO;
import ae.accumed.lookuprequestsmanager.entities.Account;
import ae.accumed.lookuprequestsmanager.entities.Facility;
import ae.accumed.lookuprequestsmanager.entities.Payers;
import ae.accumed.lookuprequestsmanager.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PayerService payerService;
    private final FacilityService facilityService;

    @Autowired
    public AccountService(AccountRepository accountRepository, PayerService payerService, FacilityService facilityService) {
        this.accountRepository = accountRepository;
        this.payerService = payerService;
        this.facilityService = facilityService;
    }

    public ArrayList<AccountDTO> findAll() {
        ArrayList<Account> accounts = (ArrayList<Account>) accountRepository.findAll();
        return (ArrayList<AccountDTO>) accounts.stream()
                .map(account ->
                        new AccountDTO(
                                account.getId(),
                                account.getIsactive(),
                                account.getUserName(),
                                new FacilityDTO(
                                        account.getFacilityByFacilityId().getId(),
                                        account.getFacilityByFacilityId().getDescription(),
                                        account.getFacilityByFacilityId().isFacilityActive(),
                                        account.getFacilityByFacilityId().getFacilityCode(),
                                        account.getFacilityByFacilityId().getFacilityName()
                                ),
                                new PayerDTO(
                                        account.getPayersByPayerId().getId(),
                                        account.getPayersByPayerId().getPayerActive(),
                                        account.getPayersByPayerId().getPayerCode(),
                                        account.getPayersByPayerId().getPayerName(),
                                        formatNumber(account.getPayersByPayerId().getCrawlerCountMs())
                                )))
                .collect(Collectors.toList());
    }

    public Account findById(int id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        return accountOptional.orElse(null);
    }

    public CreateAccountDTO findByIdAsDTO(int id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            return new CreateAccountDTO(
                    account.getId(),
                    account.getPayersByPayerId().getId(),
                    account.getFacilityByFacilityId().getId(),
                    account.getUserName(),
                    account.getPassword(),
                    account.getPassword(),
                    account.getIsactive());
        }
        return null;
    }

    public void activateAccount(int id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setIsactive(true);
            accountRepository.save(account);
        }
    }

    public void deactivateAccount(int id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setIsactive(false);
            accountRepository.save(account);
        }
    }

    public void save(CreateAccountDTO createAccountDTO) {
        Payers payer = payerService.findById(createAccountDTO.getPayerId());
        Facility facility = facilityService.findById(createAccountDTO.getFacilityId());
        Account account = new Account();
        account.setUserName(createAccountDTO.getUserName());
        account.setPassword(createAccountDTO.getPassword());
        account.setIsactive(createAccountDTO.getIsActive());
        account.setPayersByPayerId(payer);
        account.setFacilityByFacilityId(facility);
        accountRepository.save(account);
    }

    public void edit(Account account) {
        accountRepository.save(account);
    }

    private String formatNumber(double number) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(number);
    }
}
