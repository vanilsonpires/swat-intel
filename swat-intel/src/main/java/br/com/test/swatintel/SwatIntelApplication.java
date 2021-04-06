package br.com.test.swatintel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SwatIntelApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwatIntelApplication.class, args);
	}

}
