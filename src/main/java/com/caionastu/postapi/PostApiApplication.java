package com.caionastu.postapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PostApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostApiApplication.class, args);
	}

}
