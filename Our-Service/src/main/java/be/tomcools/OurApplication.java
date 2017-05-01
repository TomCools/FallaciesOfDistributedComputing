package be.tomcools;

import be.tomcools.helpers.ExternalService;
import be.tomcools.helpers.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableCircuitBreaker
@RestController
@SpringBootApplication
public class OurApplication {

    @Autowired
    private ExternalService externalService;

    @RequestMapping("normal")
    public String queryNormal(final Parameters params) {
        return externalService.callExternalService(params);
    }


    public static void main(String[] args) {
        SpringApplication.run(OurApplication.class);
    }
}
