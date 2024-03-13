package com.example.nhnacademy.producer_consumer_solution;

import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {
    private Store store;
    private String name;

    public Producer(String name, Store store) {
        this.name = name;
        this.store = store;
    }

    @Override
    public void run() {
        store.enter(name);
        while (true) {
            try {
                store.buy();
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
        }
    }
}
