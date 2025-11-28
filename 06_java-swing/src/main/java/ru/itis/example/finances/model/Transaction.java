package ru.itis.example.finances.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(
        BigDecimal amount,
        Type type,
        String description,
        LocalDateTime created)
{

    public enum Type {
        INCOME, OUTCOME;
    }
}
