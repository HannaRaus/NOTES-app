package ua.goit.goitnotes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class GoITNotesApplication {

	public static void main(String[] args) {
		log.info("The GoIT Notes application is running");
		SpringApplication.run(GoITNotesApplication.class, args);

	}

}
