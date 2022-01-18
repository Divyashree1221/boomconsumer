package com.boommotors.btp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.boommotors.btp")
public class BtpApplication {

	public static void main(String[] args) {
		SpringApplication.run(BtpApplication.class, args);
	}

}
