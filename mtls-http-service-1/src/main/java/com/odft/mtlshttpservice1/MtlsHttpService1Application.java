package com.odft.mtlshttpservice1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MtlsHttpService1Application {

	public static void main(String[] args) {
		SpringApplication.run(MtlsHttpService1Application.class, args);
	}

}
