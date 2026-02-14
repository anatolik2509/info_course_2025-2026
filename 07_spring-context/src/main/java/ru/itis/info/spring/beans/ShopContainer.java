package ru.itis.info.spring.beans;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.info.component.Shop;

@Component
@Getter
public class ShopContainer {
    private final Shop shop;

    @Autowired
    private Shop autowiredShop;

    @Autowired
    private Shop settedShop;

    public ShopContainer(Shop shop) {
        this.shop = shop;
    }

    @Autowired
    public void setSettedShop(Shop shop) {
        this.settedShop = shop;
    }
}
