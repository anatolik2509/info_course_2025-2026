package ru.itis.info.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Warehouse implements Storage {
    private final List<Product> products;

    public Warehouse() {
        products = new ArrayList<>();
    }

    @Override
    public void putProduct(Product product) {
        products.add(product);
    }

    @Override
    public List<Product> lookProducts() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public Optional<Product> takeProduct(Product product) {
        if (products.remove(product)) {
            return Optional.of(product);
        }
        return Optional.empty();
    }
}
