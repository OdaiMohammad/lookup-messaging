package ae.accumed.lookuprequestsmanager.service;

import ae.accumed.lookuprequestsmanager.dto.PayerDTO;
import ae.accumed.lookuprequestsmanager.entities.Account;
import ae.accumed.lookuprequestsmanager.entities.Payers;
import ae.accumed.lookuprequestsmanager.repository.PayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PayerService {
    private final PayerRepository payerRepository;

    @Autowired
    public PayerService(PayerRepository accountRepository) {
        this.payerRepository = accountRepository;
    }

    public ArrayList<PayerDTO> findAll(){
        ArrayList<Payers> payers = (ArrayList<Payers>) payerRepository.findAll();
        return (ArrayList<PayerDTO>) payers.stream()
                .map(payer ->
                        new PayerDTO(
                                payer.getId(),
                                payer.getPayerActive(),
                                payer.getPayerCode(),
                                payer.getPayerName()))
                .collect(Collectors.toList());
    }

    public Payers findById(int id){
        Optional<Payers> payerOptional = payerRepository.findById(id);
        return payerOptional.orElse(null);
    }

    public boolean activatePayer(int id){
        Optional<Payers> payerOptional = payerRepository.findById(id);
        if(payerOptional.isPresent()){
            Payers payer = payerOptional.get();
            payer.setPayerActive(true);
            payerRepository.save(payer);
            return true;
        } else
            return false;
    }

    public boolean deactivatePayer(int id){
        Optional<Payers> payerOptional = payerRepository.findById(id);
        if(payerOptional.isPresent()){
            Payers payer = payerOptional.get();
            payer.setPayerActive(false);
            payerRepository.save(payer);
            return true;
        } else
            return false;
    }
}
