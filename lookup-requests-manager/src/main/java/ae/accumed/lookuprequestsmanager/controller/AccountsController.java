package ae.accumed.lookuprequestsmanager.controller;

import ae.accumed.lookuprequestsmanager.dto.AccountDTO;
import ae.accumed.lookuprequestsmanager.dto.CreateAccountDTO;
import ae.accumed.lookuprequestsmanager.dto.FacilityDTO;
import ae.accumed.lookuprequestsmanager.dto.PayerDTO;
import ae.accumed.lookuprequestsmanager.entities.Account;
import ae.accumed.lookuprequestsmanager.service.AccountService;
import ae.accumed.lookuprequestsmanager.service.FacilityService;
import ae.accumed.lookuprequestsmanager.service.PayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/account")
public class AccountsController {

    private final AccountService accountService;
    private final PayerService payerService;
    private final FacilityService facilityService;

    @Autowired
    public AccountsController(AccountService accountService,PayerService payerService,FacilityService facilityService) {
        this.accountService = accountService;
        this.payerService = payerService;
        this.facilityService = facilityService;
    }

    @GetMapping
    public String accounts(Model model) {
        model.addAttribute("data", accountService.findAll());
        return "accounts";
    }

    @PostMapping("/activate/{accountId}")
    public String activateAccount(@PathVariable int accountId, Model model){
        accountService.activateAccount(accountId);
        model.addAttribute("data", accountService.findAll());
        return "redirect:/account";
    }

    @PostMapping("/deactivate/{accountId}")
    public String deactivateAccount(@PathVariable int accountId, Model model){
        accountService.deactivateAccount(accountId);
        model.addAttribute("data", accountService.findAll());
        return "redirect:/account";
    }

    @GetMapping("/new")
    public String newAccount(Model model) {
        model.addAttribute("account", new CreateAccountDTO());
        model.addAttribute("payers", payerService.findAll());
        model.addAttribute("facilities", facilityService.findAll());
        return "new_account";
    }

    @PostMapping("/new")
    public String createAccount(CreateAccountDTO createAccountDTO, Model model) {
        accountService.save(createAccountDTO);
        model.addAttribute("data", accountService.findAll());
        return "redirect:/account";
    }
}
