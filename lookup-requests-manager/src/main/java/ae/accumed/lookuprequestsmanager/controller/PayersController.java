package ae.accumed.lookuprequestsmanager.controller;

import ae.accumed.lookuprequestsmanager.dto.PayerDTO;
import ae.accumed.lookuprequestsmanager.entities.Payers;
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
@RequestMapping("/payer")
public class PayersController {

    private final PayerService payerService;

    @Autowired
    public PayersController(PayerService payerService) {
        this.payerService = payerService;
    }

    @GetMapping
    public String payers(Model model) {
        model.addAttribute("data", payerService.findAll());
        return "payers";
    }

    @PostMapping("/activate/{payerId}")
    public String activateAccount(@PathVariable int payerId, Model model){
        payerService.activatePayer(payerId);
        model.addAttribute("data", payerService.findAll());
        return "payers";
    }

    @PostMapping("/deactivate/{payerId}")
    public String deactivateAccount(@PathVariable int payerId, Model model){
        payerService.deactivatePayer(payerId);
        model.addAttribute("data", payerService.findAll());
        return "payers";
    }
}
