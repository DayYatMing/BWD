package com.bwd.brokerotrs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class BrokerotrsApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(BrokerotrsApplication.class);

    Environment env;
    public BrokerotrsApplication(Environment env) {
        this.env = env;
    }

	public static void main(String[] args) {
        SpringApplication.run(BrokerotrsApplication.class, args);
	}

    @Override
    public void run(String... args) {
        log.info("Broker OTRS started successfully.");
        log.info("===== Broker is listening on port {} =====", env.getProperty("server.port"));
    }

}
