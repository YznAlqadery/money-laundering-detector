package com.yzn.aml.detector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MoneyLaunderingDetectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyLaunderingDetectorApplication.class, args);
	}

}
