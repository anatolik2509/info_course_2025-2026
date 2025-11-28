package ru.itis.example.finances.model;

import java.math.BigDecimal;
import java.util.*;

public class Wallet {
    private BigDecimal totalAmount;
    private final Map<Long, Transaction> transactionsById;
    private final TransactionIdGenerator transactionIdGenerator;

    public Wallet() {
        totalAmount = BigDecimal.ZERO;
        transactionsById = new LinkedHashMap<>();
        transactionIdGenerator = new TransactionIdGenerator();
    }

    public Long addTransaction(Transaction transaction) {
        if (transaction.type() == Transaction.Type.INCOME) {
            totalAmount = totalAmount.add(transaction.amount());
        }
        if (transaction.type() == Transaction.Type.OUTCOME) {
            BigDecimal result = totalAmount.subtract(transaction.amount());
            if (result.signum() == -1) {
                throw new NegativeAmountException();
            }
            totalAmount = result;
        }
        Long id = transactionIdGenerator.obtainTransactionId();
        transactionsById.put(id, transaction);
        return id;
    }

    public void cancelTransaction(Long id) {
        Transaction canceledTransaction = transactionsById.get(id);
        if (canceledTransaction.type() == Transaction.Type.OUTCOME) {
            totalAmount = totalAmount.add(canceledTransaction.amount());
        }
        if (canceledTransaction.type() == Transaction.Type.INCOME) {
            BigDecimal result = totalAmount.subtract(canceledTransaction.amount());
            if (result.signum() == -1) {
                throw new NegativeAmountException();
            }
            totalAmount = result;
        }
        transactionsById.remove(id);
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactionsById.values());
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}
