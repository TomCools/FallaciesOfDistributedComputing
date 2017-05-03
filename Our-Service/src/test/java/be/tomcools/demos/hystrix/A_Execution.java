package be.tomcools.demos.hystrix;

import com.netflix.hystrix.HystrixCommand;
import org.junit.Test;
import rx.Observable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class A_Execution {

    static class SleepCommand extends HystrixCommand<String> {

        SleepCommand() {
            super(HystrixCommand.Setter.withGroupKey(() -> "TEST"));
        }

        @Override
        protected String run() throws Exception {
            Thread.sleep(500);
            return "Done!";
        }
    }

    @Test
    public void execute() {
        String result = new SleepCommand().execute();

        System.out.println(result);
    }

    @Test
    public void queue() throws ExecutionException, InterruptedException {
        Future<String> resultFuture = new SleepCommand().queue();
        String result = resultFuture.get();

        System.out.println(result);
    }

    @Test
    public void observe() {
        Observable<String> observable = new SleepCommand().observe();

        observable.subscribe(System.out::println);
    }

    @Test
    public void toObservable() {
        Observable<String> coldObservable = new SleepCommand().toObservable();

        coldObservable.subscribe(System.out::println);
    }
}
