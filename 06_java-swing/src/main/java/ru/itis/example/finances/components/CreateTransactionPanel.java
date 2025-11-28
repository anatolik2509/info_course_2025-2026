package ru.itis.example.finances.components;

import ru.itis.example.finances.controllers.CreateTransactionController;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class CreateTransactionPanel extends JPanel {
    private final JTextField descriptionInput;
    private final JComboBox<String> transactionTypeInput;
    private final JFormattedTextField amountInput;
    private final JButton submit;

    public CreateTransactionPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel descriptionLabel = new JLabel("Описание траты");
        descriptionInput = new JTextField();
        descriptionInput.setToolTipText("Описание траты");
        descriptionInput.setMaximumSize(new Dimension(1000, 100));
        transactionTypeInput = new JComboBox<>(new String[]{"Доход", "Расход"});
        transactionTypeInput.setMaximumSize(new Dimension(1000, 100));
        JLabel amountLabel = new JLabel("Сумма");
        amountInput = new JFormattedTextField();
        amountInput.setMaximumSize(new Dimension(1000, 100));
        submit = new JButton("Добавить");
        add(descriptionLabel);
        add(descriptionInput);
        add(transactionTypeInput);
        add(amountLabel);
        add(amountInput);
        add(submit);
        add(Box.createVerticalGlue());
    }

    public void addController(CreateTransactionController createTransactionController) {
        submit.addActionListener(createTransactionController);
    }

    public JTextField getDescriptionInput() {
        return descriptionInput;
    }

    public JComboBox<String> getTransactionTypeInput() {
        return transactionTypeInput;
    }

    public JFormattedTextField getAmountInput() {
        return amountInput;
    }
}
