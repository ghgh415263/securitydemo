package com.example.securitydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity(jsr250Enabled = true,securedEnabled = true)
@SpringBootApplication
public class SecuritydemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuritydemoApplication.class, args);
	}

}
