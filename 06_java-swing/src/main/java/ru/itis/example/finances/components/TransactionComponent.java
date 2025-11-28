package ru.itis.example.finances.components;

import ru.itis.example.finances.model.Transaction;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TransactionComponent extends JPanel {
    public TransactionComponent(Transaction transaction) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JLabel description = new JLabel(transaction.description());
        JLabel amount = getAmountText(transaction);
        JButton deleteButton = new JButton("Отменить");
        add(description);
        add(Box.createRigidArea(new Dimension(20, 0)));
        add(amount);
        add(Box.createHorizontalGlue());
        add(deleteButton);
        setBorder(new LineBorder(Color.BLACK));
    }

    private JLabel getAmountText(Transaction transaction) {
        if (transaction.type() == Transaction.Type.INCOME) {
            return new JLabel(transaction.amount().toPlainString());
        }
        return new JLabel("-" + transaction.amount().toPlainString());
    }
}
