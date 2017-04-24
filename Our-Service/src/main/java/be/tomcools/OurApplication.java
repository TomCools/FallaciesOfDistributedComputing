package be.tomcools;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
@RestController
@SpringBootApplication
public class OurApplication {

    @Autowired
    private ExternalService externalService;

    @RequestMapping("normal")
    public String queryNormal() {
        return externalService.callExternalService("");
    }

    @RequestMapping("hystrix")
    @com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand(groupKey = "ANNOTATION")
    public String queryHystrix() {
        return new HystrixCommand<String>(HystrixCommand.Setter.withGroupKey(() -> "ANNOTATION")) {
            @Override
            protected String run() throws Exception {
                return externalService.callExternalService("");
            }
        }.execute();

        //MyDataCenterInstanceConfig instanceConfig = new MyDataCenterInstanceConfig();
        //InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        //DiscoveryClient client = new DiscoveryClient(new ApplicationInfoManager(instanceConfig, instanceInfo), new DefaultEurekaClientConfig());

    }


    public static void main(String[] args) {
        SpringApplication.run(OurApplication.class);

            }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
