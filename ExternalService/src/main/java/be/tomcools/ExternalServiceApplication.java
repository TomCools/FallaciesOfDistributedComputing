package be.tomcools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@SpringBootApplication
@RestController
public class ExternalServiceApplication {
	private static final Random RAND = new Random();

	@RequestMapping
	public String respond(@RequestParam(value = "delay", defaultValue = "0") Long delay, @RequestParam(value = "failChance", defaultValue = "0") Double chanceToFail) throws InterruptedException {
		Thread.sleep(delay);
		double toleranceLevel = RAND.nextDouble();

		if(toleranceLevel < chanceToFail) {
			throw new RuntimeException("Bleep bloop bloop bliep, Something went horribly wrong.");
		}

		return "You have achieved... VICTORY";
	}


	public static void main(String[] args) {
		SpringApplication.run(ExternalServiceApplication.class, args);
	}
}
