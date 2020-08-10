package com.walmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.walmart" })
@EnableAutoConfiguration
@SuppressWarnings({"HideUtilityClassConstructor"})
public class HelloWorldApplication {

    public static void main(String... args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }
}
