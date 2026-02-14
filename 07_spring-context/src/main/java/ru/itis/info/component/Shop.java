package ru.itis.info.component;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Shop {
    private final Seller seller;
    private final List<Shelf> shelves;
    private final Warehouse warehouse;
}
