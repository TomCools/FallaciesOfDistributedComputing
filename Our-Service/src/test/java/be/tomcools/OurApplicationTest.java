package be.tomcools;

import com.google.common.util.concurrent.Futures;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandProperties;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE;
import static com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy.THREAD;

/**
 * Created by tomco on 24/04/2017.
 */
public class OurApplicationTest {

    @Test
    public void hystrixSemaphoreTestExecute() {
        new TestCommand(SEMAPHORE).execute();
        new TestCommand(SEMAPHORE).execute();
        new TestCommand(SEMAPHORE).execute();
    }

    @Test
    public void hystrixSemaphoreTestQueue() {
        new TestCommand(SEMAPHORE).queue();
        new TestCommand(SEMAPHORE).queue();
        new TestCommand(SEMAPHORE).queue();
    }

    @Test
    public void hystrixThreadTestExecute() {
        new TestCommand(THREAD).execute();
        new TestCommand(THREAD).execute();
        new TestCommand(THREAD).execute();
    }

    @Test
    public void hystrixThreadTestQueue() throws ExecutionException, InterruptedException {
        Future<String> f1 = new TestCommand(THREAD).queue();
        Future<String> f2 = new TestCommand(THREAD).queue();
        Future<String> f3 = new TestCommand(THREAD).queue();
        f1.get();f2.get();f3.get();
    }

    @Test
    public void hystrixSemaphoreTestExecuteLimitCalls() throws InterruptedException {
        new Thread(() -> {
            new TestCommand(SEMAPHORE).execute();
        }).start();
        new Thread(() -> {
            new TestCommand(SEMAPHORE).execute();
        }).start();
        new Thread(() -> {
            new TestCommand(SEMAPHORE).execute();
        }).start();
        Thread.sleep(1000);
    }

    static class TestCommand extends HystrixCommand<String> {

        protected TestCommand(HystrixCommandProperties.ExecutionIsolationStrategy strategy) {
            super(HystrixCommand.Setter.withGroupKey(() -> "TEST")
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                            .withExecutionIsolationStrategy(strategy)
                            .withExecutionTimeoutInMilliseconds(5000)
                    .withExecutionIsolationSemaphoreMaxConcurrentRequests(1)));
        }

        @Override
        protected String run() throws Exception {
            Thread.sleep(1000);
            return "Done!";
        }
    }

}