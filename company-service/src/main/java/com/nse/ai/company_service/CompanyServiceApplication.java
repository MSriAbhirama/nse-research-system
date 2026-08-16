package com.nse.ai.company_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.TimeZone;

@SpringBootApplication
public class CompanyServiceApplication {

	public static void main(String[] args) {
		System.out.println("Timezone: " + TimeZone.getDefault().getID());
		SpringApplication.run(CompanyServiceApplication.class, args);
		

	}

}
