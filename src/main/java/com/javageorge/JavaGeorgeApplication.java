package com.javageorge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.javageorge.entities")
@EnableJpaRepositories("com.javageorge.repositories")
public class JavaGeorgeApplication {

    static {
        // Configurações para evitar problemas com Java 24
        System.setProperty("spring.jmx.enabled", "false");
        System.setProperty("java.awt.headless", "true");
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(JavaGeorgeApplication.class);
        app.run(args);
    }

}
