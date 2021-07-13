package ae.accumed.lookuprequestsmanager.controller;

import ae.accumed.lookuprequestsmanager.dto.PayerDTO;
import ae.accumed.lookuprequestsmanager.service.PayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/new")
    public String newPayer(Model model) {
        model.addAttribute("payer", new PayerDTO());
        return "new_payer";
    }

    @PostMapping("/activate/{payerId}")
    public String activatePayer(@PathVariable int payerId, Model model) {
        payerService.activatePayer(payerId);
        model.addAttribute("data", payerService.findAll());
        return "redirect:/payer";
    }

    @PostMapping("/deactivate/{payerId}")
    public String deactivatePayer(@PathVariable int payerId, Model model) {
        payerService.deactivatePayer(payerId);
        model.addAttribute("data", payerService.findAll());
        return "redirect:/payer";
    }

    @PostMapping("/new")
    public String createPayer(PayerDTO payerDTO, Model model) {
        payerService.save(payerDTO);
        model.addAttribute("data", payerService.findAll());
        return "redirect:/payer";
    }
}
