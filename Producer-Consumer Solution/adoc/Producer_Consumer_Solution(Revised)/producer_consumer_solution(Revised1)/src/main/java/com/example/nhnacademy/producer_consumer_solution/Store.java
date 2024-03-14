package com.example.nhnacademy.producer_consumer_solution;

import java.util.concurrent.Semaphore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Store{
    private boolean isOpen; // 매장이 열려 있는지 여부
    private final Semaphore semaphore;
    private static final int MAX_PRODUCT_ACCESS = 1;
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    private String name;
    private int peopleCount;
    private int productCount;

    public Store(String name) {
        this.name = name;
        peopleCount = 0;
        productCount = 3;
        semaphore = new Semaphore(MAX_PRODUCT_ACCESS);
    }

    public void open() {
        isOpen = true;
        logger.info("{} 매장이 오픈되었습니다.", name);
    }

    public void close() {
        isOpen = false;
        logger.info("{} 매장이 닫히고, 남은 손님들을 빼내고 있습니다.", name);
        while (peopleCount > 0) {
            // 매장 안에 남아있는 손님들을 나갈 때까지 대기
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
        }
        logger.info("{} 매장이 닫혔습니다.", name);
    }

    public boolean getIsOpen(){
        return isOpen;
    }

    public String getStoreName(){
        return name;
    }

    public int getPeopleCount(){
        return peopleCount;
    }

    public void addPeopleCount(){
        peopleCount++;
    }

    public void subtractPeopleCount(){
        peopleCount--;
    }

    public int getProductCount(){
        return productCount;
    }

    public void addProductCount(){
        productCount++;
    }
    
    public void subtractProductCount(){
        productCount--;
    }

    public boolean buy() {
        return semaphore.tryAcquire();
    }

    public boolean sell() {
        return semaphore.tryAcquire();
    }

    public void buyEnd() {
        semaphore.release();
    }

    public void sellEnd() {
        semaphore.release();
    }

    public void enter(String name) {
        logger.info("{}이 {}매장에 입장하였습니다. 사람수: {}", name, getStoreName(), ++peopleCount);
    }

    public void exit(String name) {
        logger.info("{}이 {}매장에서 나갔습니다. 사람수: {}", name, getStoreName(), --peopleCount);
    }
}