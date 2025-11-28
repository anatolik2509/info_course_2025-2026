package ru.itis.example.finances.components;

import ru.itis.example.finances.AppModel;
import ru.itis.example.finances.controllers.CreateTransactionController;
import ru.itis.example.finances.model.Wallet;

import javax.swing.*;
import java.awt.*;

public class FinanceApp extends JFrame {
    private AppModel appModel;
    private final JTabbedPane tabbedPane;
    private final TotalScorePanel totalScorePanel;
    private final TransactionPanel transactionPanel;
    private final CreateTransactionPanel createTransactionPanel;

    public FinanceApp(AppModel appModel) {
        this.appModel = appModel;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);
        tabbedPane = new JTabbedPane();
        totalScorePanel = new TotalScorePanel(appModel);
        transactionPanel = new TransactionPanel(appModel.getWallet(), getSize());
        createTransactionPanel = new CreateTransactionPanel();
        CreateTransactionController createTransactionController = new CreateTransactionController(
                appModel.getWallet(), totalScorePanel, transactionPanel, createTransactionPanel
        );
        createTransactionPanel.addController(createTransactionController);
        tabbedPane.addTab("Общий счёт", totalScorePanel);
        tabbedPane.addTab("Транзакции", transactionPanel);
        tabbedPane.addTab("Добавить транзакцию", createTransactionPanel);
        add(tabbedPane);
    }
}
