package com.example.JavaSpringBootDemo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JavaSpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaSpringBootDemoApplication.class, args);
    }

    @Bean
    ApplicationRunner init() {
        return args -> {
        };
    }
}
