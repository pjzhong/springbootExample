package com.pjzhong.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Component
public class AsyncTask {

    private static Random random = new Random();

    @Async
    public Future<String> doTaskOne() throws Exception {
        System.out.println("Task one started");
        long start = System.currentTimeMillis();
        TimeUnit.MILLISECONDS.sleep(10000);
        long end  = System.currentTimeMillis();
        System.out.format("Task One finish, cost:%s millis\n", (end - start));
        return new AsyncResult<>("Task One Done");
    }


    @Async
    public Future<String> doTaskTwo() throws Exception {
        System.out.println("Task two started");
        long start = System.currentTimeMillis();
        TimeUnit.MILLISECONDS.sleep(10000);
        long end  = System.currentTimeMillis();
        System.out.format("Task two finish, cost:%s millis\n", (end - start));
        return new AsyncResult<>("Task two Done");
    }


    @Async
    public Future<String> doTaskThree() throws Exception {
        System.out.println("Task three started");
        long start = System.currentTimeMillis();
        TimeUnit.MILLISECONDS.sleep(10000);
        long end  = System.currentTimeMillis();
        System.out.format("Task three finish, cost:%s millis\n", (end - start));
        return new AsyncResult<>("Task three Done");
    }
}
