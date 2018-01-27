package be.tomcools.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalService {

    @Autowired
    RestTemplate restTemplate;

    public String callExternalService(Parameters params) {
        String requestUrl = buildRequest(params);
        return restTemplate.getForObject(requestUrl, String.class);
    }

    private String buildRequest(Parameters parameters) {
        return "http://localhost:9999" + parameters.toPathParamString();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
