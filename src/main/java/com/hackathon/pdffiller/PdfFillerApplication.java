package com.hackathon.pdffiller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class PdfFillerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfFillerApplication.class, args);
	}

}
