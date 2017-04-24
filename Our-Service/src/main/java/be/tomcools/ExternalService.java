package be.tomcools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalService {

    @Autowired
    RestTemplate restTemplate;

    public String callExternalService(String params) {
        return restTemplate.getForObject("http://localhost:9999", String.class);
    }
}
