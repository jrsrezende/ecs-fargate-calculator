package br.com.jrsr.backend.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ExpenseRequest(@NotEmpty(message = "The creditCardId cannot be empty") String creditCardId,
                             @NotEmpty(message = "The description cannot be empty") String description,
                             @NotNull(message = "The amount cannot be null") @Min(value = 0, message = "The amount must be greater than or equal to zero") Double amount,
                             @NotNull(message = "The date cannot be null") LocalDate date) {}
