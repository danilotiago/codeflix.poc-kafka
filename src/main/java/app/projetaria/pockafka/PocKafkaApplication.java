package app.projetaria.pockafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PocKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocKafkaApplication.class, args);
	}

}
