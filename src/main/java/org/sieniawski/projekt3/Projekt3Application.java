package org.sieniawski.projekt3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.sieniawski")
public class Projekt3Application {

    public static void main(String[] args) {
        SpringApplication.run(Projekt3Application.class, args);
    }

}
