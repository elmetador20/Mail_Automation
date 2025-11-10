package com.tedx.mailer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TedxMailerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TedxMailerApplication.class, args);
    }
}
