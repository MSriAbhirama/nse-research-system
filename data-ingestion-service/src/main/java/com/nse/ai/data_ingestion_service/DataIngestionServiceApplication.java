
package com.nse.ai.data_ingestion_service;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DataIngestionServiceApplication {

	public static void main(String[] args) {
		System.out.println("Timezone: " + TimeZone.getDefault().getID());
		SpringApplication.run(DataIngestionServiceApplication.class, args);
	}

}
