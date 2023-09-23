package com.example.rmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class RmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RmqApplication.class, args);
    }

}
