package com.eatclipseV2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EatclipseV2Application {

	public static void main(String[] args) {
		SpringApplication.run(EatclipseV2Application.class, args);
	}

}
