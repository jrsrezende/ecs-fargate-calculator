package br.com.jrsr.backend.service;

import br.com.jrsr.backend.dto.response.InvoiceResponse;
import br.com.jrsr.backend.exception.ResourceNotFoundException;
import br.com.jrsr.backend.model.Expense;
import br.com.jrsr.backend.model.CreditCard;
import br.com.jrsr.backend.repository.ExpenseRepository;
import br.com.jrsr.backend.repository.CreditCardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class CalculatorService {

    private final CreditCardRepository creditCardRepository;
    private final ExpenseRepository expenseRepository;

    public CalculatorService(CreditCardRepository creditCardRepository, ExpenseRepository expenseRepository) {
        this.creditCardRepository = creditCardRepository;
        this.expenseRepository = expenseRepository;
    }

    public InvoiceResponse calculateCurrentInvoice(String cardId, int month, int year) {
        CreditCard creditCard = creditCardRepository.findById(cardId);
        if (creditCard == null) {
            throw new ResourceNotFoundException("Credit card with ID " + cardId + " not found.");
        }

        int dueDay = creditCard.getDueDay();
        int closingDay = creditCard.getClosingDay();

        LocalDate referenceDate = LocalDate.of(year, month, 1);

        LocalDate dueDate = getSafeDate(referenceDate, dueDay);

        LocalDate invoiceStartDate = getSafeDate(referenceDate.minusMonths(1L), closingDay);

        LocalDate invoiceEndDate = getSafeDate(referenceDate, closingDay);

        if (!dueDate.isAfter(invoiceEndDate)) {
            dueDate = getSafeDate(referenceDate.plusMonths(1), dueDay);
        }

        List<Expense> invoiceExpenses = expenseRepository.findByCardId(cardId).stream()
                .filter(expense -> {
                    LocalDate expenseDate = LocalDate.parse(expense.getDate());
                    return expenseDate.isAfter(invoiceStartDate) && !expenseDate.isAfter(invoiceEndDate);
                })
                .toList();

        Double totalAmount = invoiceExpenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        return new InvoiceResponse(totalAmount, invoiceStartDate.plusDays(1L), invoiceEndDate, dueDate, creditCard.getName(), invoiceExpenses);
    }

    private LocalDate getSafeDate(LocalDate date, int closingDay) {
        int lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();

        int actualClosingDay = Math.min(closingDay, lastDayOfMonth);

        return date.withDayOfMonth(actualClosingDay);
    }
}
