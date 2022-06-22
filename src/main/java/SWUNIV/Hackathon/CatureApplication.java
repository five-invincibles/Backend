package SWUNIV.Hackathon;

import SWUNIV.Hackathon.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CatureApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatureApplication.class, args);
	}
}
