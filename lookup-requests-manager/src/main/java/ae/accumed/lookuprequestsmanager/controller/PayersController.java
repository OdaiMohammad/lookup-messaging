package ae.accumed.lookuprequestsmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payers")
public class PayersController {
    @GetMapping
    public String payers(Model model){
        return "payers";
    }
}
