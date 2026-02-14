package ru.itis.info;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class XmlConfigTest {
    public static void main(String[] args) {
        ApplicationContext context =
                new FileSystemXmlApplicationContext(XmlConfigTest.class.getResource("/spring-config.xml").toString());

        System.out.println("Done");

    }
}
