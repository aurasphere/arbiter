package co.aurasphere.arbiter;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Arbiter Spring Boot application.
 * 
 * @author Donato Rimenti
 */
@SpringBootApplication
public class ArbiterApplication {

	/**
	 * Starts the application.
	 * 
	 * @param args null
	 */
	public static void main(String[] args) {
		new SpringApplicationBuilder(ArbiterApplication.class)
		//.web(WebApplicationType.NONE)
		.run(args);
	}

}