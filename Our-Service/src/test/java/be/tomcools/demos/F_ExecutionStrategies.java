package be.tomcools.demos;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandProperties;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE;
import static com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy.THREAD;

/**
 * Created by tomco on 28/04/2017.
 */
public class F_ExecutionStrategies {

    static class SleepCommand extends HystrixCommand<String> {

        protected SleepCommand(HystrixCommandProperties.ExecutionIsolationStrategy strategy) {
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

    @Test
    public void hystrixThreadTestExecute() {
        new SleepCommand(THREAD).execute();
        new SleepCommand(THREAD).execute();
        new SleepCommand(THREAD).execute();
    }

    @Test
    public void hystrixThreadTestQueue() throws ExecutionException, InterruptedException {
        Future<String> f1 = new SleepCommand(THREAD).queue();
        Future<String> f2 = new SleepCommand(THREAD).queue();
        Future<String> f3 = new SleepCommand(THREAD).queue();
        f1.get();
        f2.get();
        f3.get();
    }

    @Test
    public void hystrixSemaphoreTestExecute() {
        new SleepCommand(SEMAPHORE).execute();
        new SleepCommand(SEMAPHORE).execute();
        new SleepCommand(SEMAPHORE).execute();
    }

    @Test
    public void hystrixSemaphoreTestQueue() throws ExecutionException, InterruptedException {
        Future<String> f1 = new SleepCommand(SEMAPHORE).queue();
        Future<String> f2 = new SleepCommand(SEMAPHORE).queue();
        Future<String> f3 = new SleepCommand(SEMAPHORE).queue();
        f1.get();
        f2.get();
        f3.get();
    }

    @Test
    public void hystrixSemaphoreTestExecuteLimitCalls() throws InterruptedException {
        new Thread(() -> new SleepCommand(SEMAPHORE).execute()).start();
        new Thread(() -> new SleepCommand(SEMAPHORE).execute()).start();
        new Thread(() -> new SleepCommand(SEMAPHORE).execute()).start();
        Thread.sleep(1000);
    }
}
