package com.archer.us.spider;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadPool {
    private static ScheduledExecutorService pool = Executors.newScheduledThreadPool(10);

    public static <T> Future<T> execute(Callable<T> command) {
        return pool.submit(command);
    }

    public static void shutdown() {
        pool.shutdown();
    }
}
