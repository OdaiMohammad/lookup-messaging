package ae.accumed.lookuprequestsmanager.service;

import ae.accumed.lookuprequestsmanager.entities.Account;
import ae.accumed.lookuprequestsmanager.entities.Payers;
import ae.accumed.lookuprequestsmanager.repository.AccountRepository;
import ae.accumed.lookuprequestsmanager.repository.PayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PayerService {
    private final PayerRepository accountRepository;

    @Autowired
    public PayerService(PayerRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Iterable<Payers> findAll(){
        return accountRepository.findAll();
    }

    public Payers findById(int id){
        Optional<Payers> payerOptional = accountRepository.findById(id);
        return payerOptional.orElse(null);
    }
}
