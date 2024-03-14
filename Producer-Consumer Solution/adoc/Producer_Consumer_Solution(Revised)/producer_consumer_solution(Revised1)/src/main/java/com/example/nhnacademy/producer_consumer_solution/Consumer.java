package com.example.nhnacademy.producer_consumer_solution;

import java.util.concurrent.ThreadLocalRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Consumer implements Runnable {
    private String name;
    private Store[] stores;
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    public Consumer(String name, Store[] stores) {
        this.name = name;
        this.stores = stores;
    }

    @Override
    public void run() {

        // 들어갈 가게를 결정
        Store store = stores[ThreadLocalRandom.current().nextInt(0, stores.length)];

        if (store.getIsOpen()) {
            store.enter(name);

            try {
                // 물건 고르는 시간
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10000));

                if (store.sell()) {
                    int sellFail = 0;
                    while (sellFail < 2) {
                        if (store.getProductCount() > 0) {
                            store.subtractProductCount();
                            logger.info("{}이 {}을 구매했습니다. 재고: {}", name, store.getStoreName(), store.getProductCount());
                            store.sellEnd();
                            sellFail = 2;
                        } else {
                            if (sellFail < 1) {
                                logger.info("{}이 {}을 구매하지 못해 기다립니다. 재고가 부족합니다. 재고: {}", name, store.getStoreName(), store.getProductCount());
                                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10000));
                            } else {
                                logger.warn("{}이 {}의 구매를 포기합니다. 재고: {}", name, store.getStoreName(), store.getProductCount());
                                store.sellEnd();
                            }
                            sellFail++;
                        }
                    }
                } else {
                    logger.info("{}이 구매할 권한이 없습니다.", name);
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
