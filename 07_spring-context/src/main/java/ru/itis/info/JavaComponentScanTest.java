package ru.itis.info;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.info.spring.ComponentScanSpringConfig;

public class JavaComponentScanTest {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(ComponentScanSpringConfig.class);

        System.out.println("Done");

    }
}
