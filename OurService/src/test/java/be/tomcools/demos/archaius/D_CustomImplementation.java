package be.tomcools.demos.archaius;

import com.netflix.config.*;
import org.junit.Ignore;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class D_CustomImplementation {

    @Test
    @Ignore("Manual, requires Redis to run on localhost on the default port")
    public void customImplementation() throws InterruptedException {
        RedisPolledConfigurationSource source = new RedisPolledConfigurationSource();

        AbstractPollingScheduler scheduler = new FixedDelayPollingScheduler(1000, 2000, false);

        DynamicConfiguration configuration = new DynamicConfiguration(source, scheduler);
        ConfigurationManager.install(configuration);

        Thread.sleep(200000);
    }

    public static class RedisPolledConfigurationSource implements PolledConfigurationSource {

        private Jedis jedis = new Jedis("localhost");

        private Map<String, Object> knownProperties = new HashMap<>();

        @Override
        public PollResult poll(boolean initial, Object checkPoint) throws Exception {
            jedis.keys("*").forEach(k -> {
                String value = jedis.get(k);
                knownProperties.put(k, value);
            });

            return PollResult.createFull(knownProperties);
        }
    }
}
