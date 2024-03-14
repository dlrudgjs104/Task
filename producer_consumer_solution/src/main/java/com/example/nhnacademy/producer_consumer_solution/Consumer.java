package com.example.nhnacademy.producer_consumer_solution;

import java.util.concurrent.ThreadLocalRandom;

public class Consumer implements Runnable {
    private String name;
    private Store store;

    public Consumer(String name, Store store) {
        this.name = name;
        this.store = store;
    }

    @Override
    public void run() {
        while (true) {
            try { // 가게에 들어가는 시간
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }

            store.enter(name);
            // 물건 고르는 시간
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10000));
                store.sell(name);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
    
            store.exit(name);
        }
    }
}
