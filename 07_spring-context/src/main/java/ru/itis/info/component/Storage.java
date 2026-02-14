package ru.itis.info.component;

import java.util.List;
import java.util.Optional;

public interface Storage {
    void putProduct(Product product);
    List<Product> lookProducts();
    Optional<Product> takeProduct(Product product);
}
