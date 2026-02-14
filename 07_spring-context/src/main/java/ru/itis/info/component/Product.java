package ru.itis.info.component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Product {
    private final String name;
    private final double quantity;
}
