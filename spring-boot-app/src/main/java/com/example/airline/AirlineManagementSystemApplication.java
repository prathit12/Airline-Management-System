package com.example.airline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.airline", "Application"})
public class AirlineManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirlineManagementSystemApplication.class, args);
    }
}
