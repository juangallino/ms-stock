package utn.gallino.msstock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJms

public class MsStockApplication {

	public static void main(String[] args) {

		SpringApplication.run(MsStockApplication.class, args);
		System.out.println("BUILD --SUCCESS");

	}

}
