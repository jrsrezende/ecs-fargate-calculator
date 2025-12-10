package br.com.jrsr.backend.dto.response;

import br.com.jrsr.backend.model.Expense;

import java.time.LocalDate;
import java.util.List;

public record InvoiceResponse(Double totalAmount,
                              LocalDate invoiceStartDate,
                              LocalDate invoiceEndDate,
                              LocalDate dueDate,
                              String cardName,
                              List<Expense> expenses) {}
