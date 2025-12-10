package br.com.jrsr.backend.controller;

import br.com.jrsr.backend.service.CalculatorService;
import br.com.jrsr.backend.service.CreditCardService;
import br.com.jrsr.backend.dto.request.CreditCardRequest;
import br.com.jrsr.backend.dto.response.InvoiceResponse;
import br.com.jrsr.backend.model.CreditCard;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/credit-cards")
public class CreditCardController {

    private final CreditCardService creditCardService;
    private final CalculatorService calculatorService;

    public CreditCardController(CreditCardService creditCardService, CalculatorService calculatorService) {
        this.creditCardService = creditCardService;
        this.calculatorService = calculatorService;
    }

    @PostMapping
    public ResponseEntity<CreditCard> createCreditCard(@Valid @RequestBody CreditCardRequest request) {
        CreditCard savedCreditCard = creditCardService.createCreditCard(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCreditCard);
    }

    @GetMapping
    public ResponseEntity<List<CreditCard>> getAllCards() {
        List<CreditCard> cards = creditCardService.getAllCreditCards();
        return ResponseEntity.status(HttpStatus.OK).body(cards);
    }

    @GetMapping("/{id}/invoice")
    public ResponseEntity<InvoiceResponse> getCardInvoice(@PathVariable String id, @RequestParam int month, @RequestParam int year) {
        InvoiceResponse invoice = calculatorService.calculateCurrentInvoice(id, month, year);
        return ResponseEntity.status(HttpStatus.OK).body(invoice);
    }
}
