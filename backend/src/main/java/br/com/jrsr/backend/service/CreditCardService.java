package br.com.jrsr.backend.service;

import br.com.jrsr.backend.dto.request.CreditCardRequest;
import br.com.jrsr.backend.model.CreditCard;
import br.com.jrsr.backend.repository.CreditCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;

    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }

    public CreditCard createCreditCard(CreditCardRequest request) {
        CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardId(UUID.randomUUID().toString());
        creditCard.setName(request.name());
        creditCard.setClosingDay(request.closingDay());
        creditCard.setDueDay(request.dueDay());
        creditCardRepository.save(creditCard);
        return creditCard;
    }
}
