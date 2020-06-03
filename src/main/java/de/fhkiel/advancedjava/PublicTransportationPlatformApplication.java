package de.fhkiel.advancedjava;

import de.fhkiel.advancedjava.model.node.dto.LineDto;
import de.fhkiel.advancedjava.model.node.dto.ScheduleDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PublicTransportationPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublicTransportationPlatformApplication.class, args);
	}

}
