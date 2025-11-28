package br.com.jrsr.backend.service;

import br.com.jrsr.backend.dto.request.ExpenseRequest;
import br.com.jrsr.backend.exception.ResourceNotFoundException;
import br.com.jrsr.backend.model.Expense;
import br.com.jrsr.backend.repository.ExpenseRepository;
import br.com.jrsr.backend.repository.CreditCardRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CreditCardRepository creditCardRepository;

    public ExpenseService(ExpenseRepository expenseRepository, CreditCardRepository creditCardRepository) {
        this.expenseRepository = expenseRepository;
        this.creditCardRepository = creditCardRepository;
    }

    public Expense createExpense(ExpenseRequest request) {
        if (creditCardRepository.findById(request.creditCardId()) == null) {
            throw new ResourceNotFoundException("Credit card with ID " + request.creditCardId() + " not found.");
        }

        Expense expense = new Expense();
        expense.setExpenseId(UUID.randomUUID().toString());
        expense.setCreditCardId(request.creditCardId());
        expense.setDescription(request.description());
        expense.setAmount(request.amount());
        expense.setDate(request.date().toString());
        expenseRepository.save(expense);
        return expense;

    }
}
