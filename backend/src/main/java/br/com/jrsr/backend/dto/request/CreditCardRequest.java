package br.com.jrsr.backend.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreditCardRequest(@NotEmpty(message = "Name cannot be empty") String name,
                                @NotNull(message = "Closing day cannot be null")
                                @Min(value = 1, message = "Closing day must be at least 1")
                                @Max(value = 31, message = "Closing day must be at most 31") Integer closingDay,
                                @NotNull(message = "Due day cannot be null")
                                @Min(value = 1, message = "Due day must be at least 1")
                                @Max(value = 31, message = "Due day must be at most 31") Integer dueDay) {}
