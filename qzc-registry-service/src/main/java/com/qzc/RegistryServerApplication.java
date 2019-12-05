package com.qzc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
@Slf4j
public class RegistryServerApplication {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        SpringApplication.run(RegistryServerApplication.class, args);

        log.info("*****************************************************");
        log.info("*****Registry Service Start Success, {} seconds*****", (System.currentTimeMillis() - startTime) / 1000);
        log.info("*****************************************************");
    }
}
