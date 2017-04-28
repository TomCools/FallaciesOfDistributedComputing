package be.tomcools.demos;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import org.junit.Test;

public class D_CircuitBreaker {

    static class CircuitBreakingCommand extends HystrixCommand<String> {
        protected CircuitBreakingCommand() {
            super(HystrixCommand.Setter.withGroupKey(() -> "TEST")
                    .andThreadPoolPropertiesDefaults(
                            HystrixThreadPoolProperties.Setter()
                                    .withMetricsRollingStatisticalWindowInMilliseconds(10000))
                    .andCommandPropertiesDefaults(
                            HystrixCommandProperties.Setter()
                                    .withCircuitBreakerEnabled(true)
                                    .withCircuitBreakerErrorThresholdPercentage(50)
                                    .withCircuitBreakerRequestVolumeThreshold(20)
                                    .withCircuitBreakerSleepWindowInMilliseconds(5000)));
        }

        @Override
        protected String run() throws Exception {
            throw new RuntimeException("Just to showcase Circuit Breaker settings");
        }
    }

    @Test
    public void circuitBreaker() {
        String result = new CircuitBreakingCommand().execute();
    }
}
