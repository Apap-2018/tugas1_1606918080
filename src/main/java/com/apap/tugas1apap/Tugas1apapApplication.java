package com.apap.tugas1apap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
public class Tugas1apapApplication {

    public static void main(String[] args) {
        SpringApplication.run(Tugas1apapApplication.class, args);
    }
}
