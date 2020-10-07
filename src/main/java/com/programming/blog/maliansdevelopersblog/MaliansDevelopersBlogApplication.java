package com.programming.blog.maliansdevelopersblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.programming.blog"})
public class MaliansDevelopersBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaliansDevelopersBlogApplication.class, args);
	}

}
