package com.microservice.mscclientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main application class for the Microservicio de Clientes.
 * Implements Clean Architecture with Spring Boot.
 */
@SpringBootApplication
@EnableTransactionManagement
public class MscClientesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MscClientesApplication.class, args);
    }
}
