package com.microapp2.Micro.App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.microapp2.Micro.App.Entities")
@EnableJpaRepositories("com.microapp2.Micro.App.Repositories")
public class MicroAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroAppApplication.class, args);
    }
}