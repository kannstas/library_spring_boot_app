package ru.nastya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:database.properties")
public class LibrarySpringAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrarySpringAppApplication.class, args);
	}

}
