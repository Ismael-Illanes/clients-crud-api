package com.clientscrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication
public class ClientscrudApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();
        String dbURL = dotenv.get("DB_URL");
        String dbPass = dotenv.get("DB_PASS");
		System.out.println(dbURL + " " + dbPass) ;
		SpringApplication.run(ClientscrudApplication.class, args);
	
	}


}
