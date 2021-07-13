package ae.accumed.lookuprequestsmanager.controller;

import ae.accumed.lookuprequestsmanager.dto.PayerDTO;
import ae.accumed.lookuprequestsmanager.service.PayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
    public String createPayer(@Valid @ModelAttribute("payer") PayerDTO payerDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("payer", payerDTO);
            return "new_payer";
        }
        payerService.save(payerDTO);
        model.addAttribute("data", payerService.findAll());
        return "redirect:/payer";
    }
}
