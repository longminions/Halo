package com.longtv.halo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
class HaloApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaloApplication.class, args);
	}

}
