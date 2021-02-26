package com.db.interpretor;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
@SpringBootApplication
@ComponentScan({ "com.db.interpretor.*" })
public class InterpretorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterpretorServiceApplication.class, args);
		setProperties();
	}

	
	  private static void setProperties() {
	  TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	  System.getProperties().setProperty("oracle.jdbc.J2EE13Compliant", "true"); }
	 
}
