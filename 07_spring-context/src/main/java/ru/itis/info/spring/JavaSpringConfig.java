package ru.itis.info.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.itis.info.component.*;

import java.util.List;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Configuration
public class JavaSpringConfig {

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public Product product() {
        return new Product("Штука", Math.random());
    }

    @Bean
    public Shelf foodShelf(Product product) {
        Shelf shelf = new Shelf();
        shelf.putProduct(product);
        return shelf;
    }

    @Bean
    public Shelf clothesShelf(Product product) {
        Shelf shelf = new Shelf();
        shelf.putProduct(product);
        return shelf;
    }

    @Bean
    public Seller seller() {
        return new Seller("Super", "Seller2");
    }

    @Bean
    public Warehouse warehouse() {
        return new Warehouse();
    }

    @Bean
    public Shop shop(Seller seller, List<Shelf> shelves, Warehouse warehouse) {
        return new Shop(seller, shelves, warehouse);
    }
}
