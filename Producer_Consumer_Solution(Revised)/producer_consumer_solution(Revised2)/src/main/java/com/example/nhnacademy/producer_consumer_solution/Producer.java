package com.example.nhnacademy.producer_consumer_solution;

import java.util.concurrent.ThreadLocalRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Producer implements Runnable {
    private Store[] stores;
    private String name;
    private static final int MAX_PRODUCT = 5;
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    public Producer(String name, Store[] stores) {
        this.name = name;
        this.stores = stores;
    }

    public String getProducerName(){
        return name;
    }

    @Override
    public void run() {

        // 들어갈 가게를 결정
        Store store = stores[ThreadLocalRandom.current().nextInt(0, stores.length)];
        if (store.getIsOpen()) {
            store.enter(name);

            try {
                // 납품하는 시간
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10000));

                if (store.buy()) {
                    int buyFail = 0;
                    while (buyFail < 2) {
                        if (store.getProductCount() < MAX_PRODUCT) {
                            store.addProductCount();
                            logger.info("{}이 {}을 납품했습니다. 재고: {}", name, store.getStoreName(), store.getProductCount());
                            store.buyEnd();
                            buyFail = 2;
                        } else {
                            if (buyFail < 1) {
                                logger.info("{}이 {}을 납품하지 못해 기다립니다. 재고가 가득찼습니다. 재고: {}", name, store.getStoreName(), store.getProductCount());
                                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10000));
                            } else {
                                logger.warn("{}이 {}을 납품을 포기합니다. 재고: {}", name, store.getStoreName(), store.getProductCount());
                                store.buyEnd();
                            }
                            buyFail++;
                        }
                    }
                } else {
                    logger.info("{}이 납품할 권한이 없습니다.", name);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
            store.exit(name);
        }
        else{
            logger.info("{}매장이 닫혀 있습니다.", name);
        }
        
    }
}
