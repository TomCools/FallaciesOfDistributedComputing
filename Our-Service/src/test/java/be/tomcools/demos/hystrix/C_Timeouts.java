package be.tomcools.demos.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandProperties;
import org.junit.Test;

public class C_Timeouts {

    static class DelayedCommand extends HystrixCommand<String> {
        private int delay;

        protected DelayedCommand(int duration) {
            super(HystrixCommand.Setter.withGroupKey(() -> "TEST")
                    .andCommandPropertiesDefaults(
                            HystrixCommandProperties.Setter()
                                    .withExecutionTimeoutEnabled(true)
                                    .withExecutionTimeoutInMilliseconds(1000)));
            this.delay = duration;
        }

        @Override
        protected String run() throws Exception {
            Thread.sleep(delay);
            return "Done!";
        }
    }

    @Test
    public void quickEnough() {
        new DelayedCommand(100).execute();
    }

    @Test
    public void tooSlow() {
        new DelayedCommand(1500).execute();
    }
}
