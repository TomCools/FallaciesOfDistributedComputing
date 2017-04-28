package be.tomcools;

import be.tomcools.helpers.ExternalService;
import be.tomcools.helpers.Parameters;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandKey;
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

    private static DynamicStringProperty fallBackText =
            DynamicPropertyFactory.getInstance().getStringProperty("fallback.text","fallback");


    @RequestMapping("normal")
    public String queryNormal(final Parameters params) {
        return externalService.callExternalService(params);
    }

    @RequestMapping("hystrix")
    public String queryHystrix(final Parameters parameters) {
        return new HystrixCommand<String>(HystrixCommand.Setter.withGroupKey(() -> "Pure Hystrix")
                .andCommandKey(HystrixCommandKey.Factory.asKey("Hystrix"))) {


            @Override
            protected String run() throws Exception {
                return externalService.callExternalService(parameters);
            }

            @Override
            protected String getFallback() {
                System.out.println("Falling BACK");
                return fallBackText.get();
            }
        }.execute();
    }

    public static void main(String[] args) {
        SpringApplication.run(OurApplication.class);
    }
}
