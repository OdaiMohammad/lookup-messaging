package ae.accumed.lookuprequestsmanager.controller;

import ae.accumed.lookuprequestsmanager.dto.CreateAccountDTO;
import ae.accumed.lookuprequestsmanager.entities.Account;
import ae.accumed.lookuprequestsmanager.service.AccountService;
import ae.accumed.lookuprequestsmanager.service.FacilityService;
import ae.accumed.lookuprequestsmanager.service.PayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountsController {

    private final AccountService accountService;
    private final PayerService payerService;
    private final FacilityService facilityService;

    @Autowired
    public AccountsController(AccountService accountService, PayerService payerService, FacilityService facilityService) {
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
    public String activateAccount(@PathVariable int accountId, Model model) {
        accountService.activateAccount(accountId);
        model.addAttribute("data", accountService.findAll());
        return "redirect:/account";
    }

    @PostMapping("/deactivate/{accountId}")
    public String deactivateAccount(@PathVariable int accountId, Model model) {
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
    public String createAccount(@Valid @ModelAttribute("account") CreateAccountDTO createAccountDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("account", createAccountDTO);
            model.addAttribute("payers", payerService.findAll());
            model.addAttribute("facilities", facilityService.findAll());
            return "new_account";
        }
        accountService.save(createAccountDTO);
        model.addAttribute("data", accountService.findAll());
        return "redirect:/account";
    }

    @GetMapping("/edit/{accountId}")
    public String editAccount(@PathVariable int accountId, Model model) {
        CreateAccountDTO accountDTO = accountService.findByIdAsDTO(accountId);
        model.addAttribute("account", accountDTO);
        model.addAttribute("payers", payerService.findAll());
        model.addAttribute("facilities", facilityService.findAll());
        if (accountDTO == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        return "edit_account";
    }

    @PostMapping("/edit/{accountId}")
    public String editAccount(@PathVariable int accountId, @Valid @ModelAttribute("account") CreateAccountDTO accountDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("account", accountDTO);
            return "edit_account";
        }
        Account account = accountService.findById(accountId);
        account.setPayersByPayerId(payerService.findById(accountDTO.getPayerId()));
        account.setFacilityByFacilityId(facilityService.findById(accountDTO.getFacilityId()));
        account.setUserName(accountDTO.getUserName());
        if (!accountDTO.getPassword().isEmpty() && accountDTO.getPassword() != null)
            account.setPassword(accountDTO.getPassword());
        account.setIsactive(accountDTO.getIsActive());
        accountService.edit(account);
        model.addAttribute("data", facilityService.findAll());
        return "redirect:/account";
    }
}
