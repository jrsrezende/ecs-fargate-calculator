package br.com.jrsr.backend.controller;

import br.com.jrsr.backend.service.ExpenseService;
import br.com.jrsr.backend.dto.request.ExpenseRequest;
import br.com.jrsr.backend.model.Expense;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<Expense> createCharge(@Valid @RequestBody ExpenseRequest request) {
        Expense savedExpense = expenseService.createExpense(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
    }
}
