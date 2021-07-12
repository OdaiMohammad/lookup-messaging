package ae.accumed.lookuprequestsmanager.controller;

import ae.accumed.lookuprequestsmanager.dto.AccountDTO;
import ae.accumed.lookuprequestsmanager.dto.FacilityDTO;
import ae.accumed.lookuprequestsmanager.dto.PayerDTO;
import ae.accumed.lookuprequestsmanager.entities.Account;
import ae.accumed.lookuprequestsmanager.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountService accountService;

    @Autowired
    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String accounts(Model model) {

        ArrayList<Account> accounts = (ArrayList<Account>) accountService.findAll();
        List<AccountDTO> accountDTOs = accounts.stream()
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
        model.addAttribute("data", accountDTOs);
        return "accounts";
    }
}
