package ru.itis.example.finances.components;

import ru.itis.example.finances.model.Transaction;
import ru.itis.example.finances.model.Wallet;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class TransactionPanel extends JPanel {
    private final Wallet wallet;

    public TransactionPanel(Wallet wallet, Dimension size) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.wallet = wallet;
        wallet.getTransactions()
                        .stream().map(TransactionComponent::new)
                        .forEach(this::add);
    }

    public void addTransaction(Transaction transaction) {
        TransactionComponent transactionComponent = new TransactionComponent(transaction);
        transactionComponent.setSize(getSize().width, transactionComponent.getPreferredSize().height);
        add(transactionComponent, 0);
    }

    public void removeTransaction(TransactionComponent transactionComponent) {
        remove(transactionComponent);
    }
}
