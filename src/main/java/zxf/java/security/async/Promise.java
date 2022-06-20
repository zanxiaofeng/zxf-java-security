package zxf.java.security.async;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Promise<T> {
    private Supplier<T> supplier;
    private Promise<?> prevPromise;
    private Function<Object, T> callback;
    private Promise<?> nextPromise;
    private Consumer<T> asyncConsumer;

    private Promise(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    private Promise(Promise<?> prevPromise, Function<Object, T> callback) {
        this.prevPromise = prevPromise;
        this.callback = callback;
    }

    public <O> Promise<O> then(Function<T, O> callback) {
        Promise<O> newPromise = new Promise(this, callback);
        this.nextPromise = newPromise;
        return newPromise;
    }

    public T getSync() {
        if (supplier != null) {
            return supplier.get();
        }
        Object result = prevPromise.getSync();
        return this.callback.apply(result);
    }

    public void getAsync(Consumer<T> asyncConsumer) {
        this.asyncConsumer = asyncConsumer;
        this.getAsync();
    }

    private void getAsync() {
        if (supplier != null) {
            new Thread(() -> {
                try {
                    T result = supplier.get();
                    nextPromise.asyncCallback(result);
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }).start();
            return;
        }

        prevPromise.getAsync();
    }

    private void asyncCallback(Object input) {
        T result = this.callback.apply(input);
        if (asyncConsumer != null) {
            asyncConsumer.accept(result);
            return;
        }
        nextPromise.asyncCallback(result);
    }

    public static <T> Promise<T> resolve(Supplier<T> supplier) {
        return new Promise<>(supplier);
    }

    public static void main(String[] args) throws InterruptedException {
        String abc = Promise.resolve(() -> 12345).then(x -> x * x).then(x -> x.toString()).getSync();
        System.out.println("Sync: " + abc);

        Promise.resolve(() -> 12345).then(x -> x * x).then(x -> x.toString()).getAsync(bbc -> {
            System.out.println("Async: " + bbc);
        });

        Thread.sleep(2000);
        System.out.println("exit");
    }
}
