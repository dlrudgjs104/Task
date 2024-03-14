package com.example.nhnacademy.producer_consumer_solution;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) {
        final int OPERATION_TIME = 5 * 60 * 1000; // 5분(밀리초 단위)

        // ThreadPool 생성(생산자와 소비자 동시 입장 수 관리)
        ExecutorService executor = Executors.newFixedThreadPool(5);

        String[] productList = {"[apple]", "[fineapple]", "[water]", "[milk]", "[meat]", "[snack]", "[beaf]", "[vegetable]", "[meal]"};
        String[] producerList = {"[producer1]", "[producer2]", "[producer3]", "[producer4]", "[producer5]", "[producer6]", "[producer7]", "[producer8]", "[producer9]"};
        String[] consumerList = {"[man1]", "[man2]", "[man3]", "[man4]", "[man5]", "[man6]", "[man7]", "[man8]", "[man9]"};
        Store[] stores = new Store[productList.length];

        for (int i = 0; i < productList.length; i++) {
            stores[i] = (new Store(productList[i]));
            stores[i].open(); // 각 매장을 오픈
        }
        
        for(int i = 0; i < 9; i++){
            Producer producer = new Producer(producerList[i], stores);
            Consumer consumer = new Consumer(consumerList[i], stores);
        
            executor.submit(producer);
            executor.submit(consumer);
        }

        // 5분 후에 매장을 닫음
        try {
            Thread.sleep(OPERATION_TIME); // 5분 대기
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread Interrupted");
        }
        
        // 매장 닫음
        for (Store store : stores) {
            store.close(); // 각 매장을 닫음
        }
        
        // 모든 작업이 완료될 때까지 대기
        executor.shutdown();
        while(!executor.isTerminated()){
        }

        System.out.println("모든 작업이 완료 되었습니다.");
    }
}
