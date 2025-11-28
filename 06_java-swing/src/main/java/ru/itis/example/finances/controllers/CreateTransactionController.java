package ru.itis.example.finances.controllers;

import ru.itis.example.finances.components.CreateTransactionPanel;
import ru.itis.example.finances.components.FinanceApp;
import ru.itis.example.finances.components.TotalScorePanel;
import ru.itis.example.finances.components.TransactionPanel;
import ru.itis.example.finances.model.Transaction;
import ru.itis.example.finances.model.Wallet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateTransactionController implements ActionListener {
    private final Wallet wallet;
    private final TotalScorePanel totalScorePanel;
    private final TransactionPanel transactionPanel;
    private final CreateTransactionPanel createTransactionPanel;

    public CreateTransactionController(Wallet wallet, TotalScorePanel totalScorePanel, TransactionPanel transactionPanel, CreateTransactionPanel createTransactionPanel) {
        this.wallet = wallet;
        this.totalScorePanel = totalScorePanel;
        this.transactionPanel = transactionPanel;
        this.createTransactionPanel = createTransactionPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String description = createTransactionPanel.getDescriptionInput().getText();
        Transaction.Type transactionType = toTransactionType(
                (String) createTransactionPanel.getTransactionTypeInput().getSelectedItem()
        );
        BigDecimal amount = new BigDecimal(createTransactionPanel.getAmountInput().getText());
        Transaction transaction = new Transaction(amount, transactionType, description, LocalDateTime.now());
        wallet.addTransaction(transaction);
        totalScorePanel.updateScore();
        transactionPanel.addTransaction(transaction);
    }

    private Transaction.Type toTransactionType(String value) {
        if (value.equals("Доход")) {
            return Transaction.Type.INCOME;
        }
        if (value.equals("Расход")) {
            return Transaction.Type.OUTCOME;
        }
        throw new IllegalArgumentException();
    }
}
