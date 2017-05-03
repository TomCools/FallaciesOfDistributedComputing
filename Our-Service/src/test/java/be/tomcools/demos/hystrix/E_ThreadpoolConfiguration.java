package be.tomcools.demos.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import org.junit.Test;

public class E_ThreadpoolConfiguration {
    static class DefaultThreadpoolConfiguration extends HystrixCommand<String> {
        protected DefaultThreadpoolConfiguration() {
            super(HystrixCommand.Setter.withGroupKey(() -> "TEST")
                    .andThreadPoolPropertiesDefaults(
                            HystrixThreadPoolProperties.Setter()
                                    .withCoreSize(10)
                                    .withAllowMaximumSizeToDivergeFromCoreSize(false)));
        }

        @Override
        protected String run() throws Exception {
            throw new RuntimeException("Just to showcase Threadpool settings");
        }
    }

    static class FlexibleThreadpoolSizeConfiguration extends HystrixCommand<String> {
        protected FlexibleThreadpoolSizeConfiguration() {
            super(HystrixCommand.Setter.withGroupKey(() -> "TEST")
                    .andThreadPoolPropertiesDefaults(
                            HystrixThreadPoolProperties.Setter()
                                    .withCoreSize(10)
                                    .withMaximumSize(20) //Not setting it defaults to 10
                                    .withAllowMaximumSizeToDivergeFromCoreSize(true)
                                    .withKeepAliveTimeMinutes(1)));
        }

        @Override
        protected String run() throws Exception {
            throw new RuntimeException("Just to showcase Threadpool settings");
        }
    }






    @Test
    public void Test() {
        new DefaultThreadpoolConfiguration();
        new FlexibleThreadpoolSizeConfiguration();
    }
}
