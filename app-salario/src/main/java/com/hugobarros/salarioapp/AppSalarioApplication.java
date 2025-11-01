package com.hugobarros.salarioapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@ServletComponentScan
@SpringBootApplication
public class AppSalarioApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppSalarioApplication.class, args);
    }
}
