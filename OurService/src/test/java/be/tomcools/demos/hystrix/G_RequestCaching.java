package be.tomcools.demos.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class G_RequestCaching {
    static int COUNTER = 0;

    static class GetUserCommand extends HystrixCommand<String> {
        private long id;

        protected GetUserCommand(long id) {
            super(HystrixCommand.Setter.withGroupKey(() -> "TEST")
                    .andCommandPropertiesDefaults(
                            HystrixCommandProperties.Setter()
                                    .withRequestCacheEnabled(true))); //DEFAULT
            this.id = id;
        }

        @Override
        protected String run() throws Exception {
            Thread.sleep(500);
            COUNTER += 1;
            return "Done! Here is your user name :-)";
        }

        @Override
        protected String getCacheKey() {
            return Long.toString(id); //DEFAULT returns NULL => Do not cache
        }
    }

    @Test
    public void hystrixThreadTestExecute() {
        new GetUserCommand(1).execute();
        new GetUserCommand(1).execute();
        new GetUserCommand(1).execute();

        System.out.println(COUNTER);
    }






    @Before
    public void init() {
        HystrixRequestContext.initializeContext();
    }

    @After
    public void cleanup() {
        HystrixRequestContext.getContextForCurrentThread().shutdown();
    }
}
