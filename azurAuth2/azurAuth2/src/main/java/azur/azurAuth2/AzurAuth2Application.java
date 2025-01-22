package azur.azurAuth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
@RestController
@RequestMapping("/api")

public class AzurAuth2Application {

	@GetMapping("/login")
	public String welcome(){
		return "Spring Boot + Azure Active Directory auth example !!! DEMO";
	}

	public static void main(String[] args) {
		SpringApplication.run(AzurAuth2Application.class, args);
	}

}