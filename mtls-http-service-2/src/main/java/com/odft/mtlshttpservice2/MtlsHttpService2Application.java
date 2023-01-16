package com.odft.mtlshttpservice2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MtlsHttpService2Application {

	public static void main(String[] args) {
		SpringApplication.run(MtlsHttpService2Application.class, args);
	}

}
