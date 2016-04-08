package com.tjazi.routing.endpoint.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.tjazi")
public class EndpointTemplateCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(EndpointTemplateCoreApplication.class, args);
	}
}
