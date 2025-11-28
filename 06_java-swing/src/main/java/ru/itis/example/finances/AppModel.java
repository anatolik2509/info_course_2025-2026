package ru.itis.example.finances;

import ru.itis.example.finances.model.Wallet;

public class AppModel {
    private final Wallet wallet;

    public AppModel() {
        this.wallet = new Wallet();
    }

    public Wallet getWallet() {
        return wallet;
    }
}
