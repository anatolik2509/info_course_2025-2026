package ru.itis.example.finances;

import ru.itis.example.finances.components.FinanceApp;

public class Main {
    public static void main(String[] args) {
        AppModel appModel = new AppModel();
        FinanceApp financeApp = new FinanceApp(appModel);
        financeApp.setVisible(true);
    }
}
