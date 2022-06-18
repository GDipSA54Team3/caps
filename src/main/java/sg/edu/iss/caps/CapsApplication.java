package sg.edu.iss.caps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import sg.edu.iss.caps.appInitialization.AppInitializator;

@SpringBootApplication
public class CapsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapsApplication.class, args);
	}

}
