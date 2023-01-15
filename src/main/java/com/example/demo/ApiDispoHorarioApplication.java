package com.example.demo;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiDispoHorarioApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiDispoHorarioApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		String originalInput = "test input";
		String encodedString = new String(org.apache.commons.codec.binary.Base64.encodeBase64(originalInput.getBytes()));
		String decodedString = new String(org.apache.commons.codec.binary.Base64.decodeBase64(encodedString.getBytes()));
		System.out.println("en - " + encodedString);
		System.out.println("de - " + decodedString);
	}
}
