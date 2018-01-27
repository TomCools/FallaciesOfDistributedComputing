package be.tomcools;

import be.tomcools.helpers.ExternalService;
import be.tomcools.helpers.Parameters;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.hystrix.HystrixCommand;
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

    static {
        System.setProperty("archaius.configurationSource.additionalUrls", "http://localhost:8081/properties");
    }

    @Autowired
    private ExternalService externalService;

    DynamicStringProperty fallbackText =
            DynamicPropertyFactory.getInstance()
                .getStringProperty("fallback.text", "default");

    @RequestMapping("normal")
    public String queryNormal(final Parameters params) {
        try {
            return externalService.callExternalService(params);
        } catch (Exception ex) {
            return "something went wrong";
        }
    }

    @RequestMapping("hystrix")
    public String queryHystrix(final Parameters params) {
        HystrixCommand<String> command = new HystrixCommand<String>(HystrixCommand.Setter.withGroupKey(() -> "HystrixCall")) {
            @Override
            protected String run() throws Exception {
                return externalService.callExternalService(params);
            }

            @Override
            protected String getFallback() {
                return fallbackText.get();
            }
        };
        return command.execute();
    }

    public static void main(String[] args) {
        SpringApplication.run(OurApplication.class);
    }
}
