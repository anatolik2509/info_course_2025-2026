package ru.itis.info;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.info.spring.JavaSpringConfig;

public class JavaConfigTest {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(JavaSpringConfig.class);

        System.out.println("Done");

    }
}
