package com.fundtransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FundTransferApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundTransferApiApplication.class, args);
	}

}
