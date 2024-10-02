package com.spring.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootRestApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRestApiServiceApplication.class, args);
    }

}
