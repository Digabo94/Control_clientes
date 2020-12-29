package mx.com.gm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Con agregar el @SpringBootApplication vamos a poder trabajar con las aplicaciones spring
@SpringBootApplication
public class HolaSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HolaSpringApplication.class, args);
	}

}
