package com.ekan.avaliacaoekan;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AvaliacaoEkanApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		
		SpringApplication.run(AvaliacaoEkanApplication.class, args);
	}

}
