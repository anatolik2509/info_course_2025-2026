package ru.itis.example.finances.model;

public class TransactionIdGenerator {
    private Long lastId;

    public TransactionIdGenerator() {
        lastId = 1L;
    }

    public synchronized Long obtainTransactionId() {
        return lastId++;
    }
}
