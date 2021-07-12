package ae.accumed.lookuprequestsmanager.controller;

import ae.accumed.lookuprequestsmanager.dto.PayerDTO;
import ae.accumed.lookuprequestsmanager.entities.Payers;
import ae.accumed.lookuprequestsmanager.service.PayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/payers")
public class PayersController {

    private final PayerService payerService;

    @Autowired
    public PayersController(PayerService payerService) {
        this.payerService = payerService;
    }

    @GetMapping
    public String payers(Model model) {
        ArrayList<Payers> payers = (ArrayList<Payers>) payerService.findAll();
        List<PayerDTO> payerDTOs = payers.stream()
                .map(payer ->
                        new PayerDTO(
                                payer.getId(),
                                payer.getPayerActive(),
                                payer.getPayerCode(),
                                payer.getPayerName()))
                .collect(Collectors.toList());
        model.addAttribute("data", payerDTOs);
        return "payers";
    }
}
