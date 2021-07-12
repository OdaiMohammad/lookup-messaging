package ae.accumed.lookuprequestsmanager.service;

import ae.accumed.lookuprequestsmanager.dto.AccountDTO;
import ae.accumed.lookuprequestsmanager.dto.FacilityDTO;
import ae.accumed.lookuprequestsmanager.dto.PayerDTO;
import ae.accumed.lookuprequestsmanager.entities.Account;
import ae.accumed.lookuprequestsmanager.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ArrayList<AccountDTO> findAll(){
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
                                        account.getFacilityByFacilityId().getFacilityCode(),
                                        account.getFacilityByFacilityId().getFacilityName()
                                ),
                                new PayerDTO(
                                        account.getPayersByPayerId().getId(),
                                        account.getPayersByPayerId().getPayerActive(),
                                        account.getPayersByPayerId().getPayerCode(),
                                        account.getPayersByPayerId().getPayerName()
                                )))
                .collect(Collectors.toList());
    }

    public Account findById(int id){
        Optional<Account> accountOptional = accountRepository.findById(id);
        return accountOptional.orElse(null);
    }

    public boolean activateAccount(int id){
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent()){
            Account account = accountOptional.get();
            account.setIsactive(true);
            accountRepository.save(account);
            return true;
        } else
            return false;
    }

    public boolean deactivateAccount(int id){
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent()){
            Account account = accountOptional.get();
            account.setIsactive(false);
            accountRepository.save(account);
            return true;
        } else
            return false;
    }
}
