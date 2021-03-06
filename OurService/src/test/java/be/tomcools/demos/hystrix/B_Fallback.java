package be.tomcools.demos.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandProperties;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class B_Fallback {

    static class BasicCommand extends HystrixCommand<String> {

        protected BasicCommand() {
            super(HystrixCommand.Setter.withGroupKey(() -> "TEST")
                    .andCommandPropertiesDefaults(
                            HystrixCommandProperties.Setter()
                                    .withFallbackEnabled(true))); //DEFAULT
        }

        @Override
        protected String run() throws Exception {
            throw new RuntimeException("Just to show off Fallback");
        }

        @Override
        protected String getFallback() {
            return "fallback";
        }
    }

    @Test
    public void fallBack() {
        String result = new BasicCommand().execute();

        assertThat(result).isEqualTo("fallback");
    }
}
