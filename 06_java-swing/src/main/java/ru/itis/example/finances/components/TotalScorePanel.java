package ru.itis.example.finances.components;

import ru.itis.example.finances.AppModel;
import ru.itis.example.finances.model.Wallet;

import javax.swing.*;

public class TotalScorePanel extends JPanel {
    private final AppModel appModel;
    private final JLabel totalScoreText;

    public TotalScorePanel(AppModel appModel) {
        this.appModel = appModel;
        totalScoreText = new JLabel("Нет данных(");
        add(totalScoreText);
    }

    public void updateScore() {
        String totalAmount = appModel.getWallet().getTotalAmount().toString();
        String scoreText = "У вас %s рублей. ".formatted(totalAmount);
        String additionalString = getAdditionalText(appModel.getWallet().getTotalAmount().doubleValue());
        totalScoreText.setText(scoreText + additionalString);
    }

    private String getAdditionalText(Double score) {
        if (score < 1e-5) {
            return "Советую найти работу чтоли...";
        }
        if (score < 50) {
            return "Хватит на проезд";
        }
        if (score < 500) {
            return "Хватит на шаурму";
        }
        if (score < 2000) {
            return "Ого...";
        }
        if (score < 20000) {
            return "Ого!";
        }
        return "Донаты скидывать на карту 1234567891011";
    }
}
