package com.javageorge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.javageorge")
@EntityScan(basePackages = "com.javageorge.entities")
@EnableJpaRepositories(basePackages = "com.javageorge.repositories")
public class JavaGeorgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaGeorgeApplication.class, args);
    }

}
