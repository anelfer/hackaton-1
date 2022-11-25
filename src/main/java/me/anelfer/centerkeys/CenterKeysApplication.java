package me.anelfer.centerkeys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CenterKeysApplication {

    public static void main(String[] args) {
        SpringApplication.run(CenterKeysApplication.class, args);
    }

}
