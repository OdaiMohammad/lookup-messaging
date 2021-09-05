package ae.accumed.lookuprequestsmanager.service;

import ae.accumed.lookuprequestsmanager.dto.PayerDTO;
import ae.accumed.lookuprequestsmanager.entities.Payers;
import ae.accumed.lookuprequestsmanager.repository.PayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PayerService {
    private final PayerRepository payerRepository;

    @Autowired
    public PayerService(PayerRepository accountRepository) {
        this.payerRepository = accountRepository;
    }

    public ArrayList<PayerDTO> findAll() {
        ArrayList<Payers> payers = (ArrayList<Payers>) payerRepository.findAll();
        return (ArrayList<PayerDTO>) payers.stream()
                .map(payer ->
                        new PayerDTO(
                                payer.getId(),
                                payer.getPayerActive(),
                                payer.getPayerCode(),
                                payer.getPayerName(),
                                formatNumber(payer.getCrawlerCountMs())
                        )
                )
                .collect(Collectors.toList());
    }

    public Payers findById(int id) {
        Optional<Payers> payerOptional = payerRepository.findById(id);
        return payerOptional.orElse(null);
    }

    public void save(PayerDTO payerDTO) {
        Payers payers = new Payers();
        payers.setPayerName(payerDTO.getPayerName());
        payers.setPayerCode(payerDTO.getPayerCode());
        payers.setPayerActive(payerDTO.getIsActive());
        payers.setCrawlerCountMs(Integer.parseInt(payerDTO.getCrawlerCountMs()));
        payerRepository.save(payers);
    }

    public void activatePayer(int id) {
        Optional<Payers> payerOptional = payerRepository.findById(id);
        if (payerOptional.isPresent()) {
            Payers payer = payerOptional.get();
            payer.setPayerActive(true);
            payerRepository.save(payer);
        }
    }

    public void deactivatePayer(int id) {
        Optional<Payers> payerOptional = payerRepository.findById(id);
        if (payerOptional.isPresent()) {
            Payers payer = payerOptional.get();
            payer.setPayerActive(false);
            payerRepository.save(payer);
        }
    }

    private String formatNumber(double number) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(number);
    }
}
