package com.emnsoft.emnsurvey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.github.cloudyrock.spring.v5.EnableMongock;

@EnableMongock
@SpringBootApplication
public class EmnSurveyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmnSurveyApiApplication.class, args);
	}
}
