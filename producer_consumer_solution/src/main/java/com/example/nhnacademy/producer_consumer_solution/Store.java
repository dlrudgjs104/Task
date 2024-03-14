package com.example.nhnacademy.producer_consumer_solution;

public class Store {
    private static final int MAX_PRODUCT = 10;
    private static final int MAX_PEOPLE = 5;

    private int productCount;
    private int peopleCount;

    public Store() {
        productCount = 5;
        peopleCount = 0;
    }

    public int getProductCount() {
        return productCount;
    }

    public int getpeopleCount() {
        return peopleCount;
    }

    public void enter(String name) {
        if (peopleCount < MAX_PEOPLE) {
            System.out.println(name + "이 매장에 입장하였습니다. 매장 내 사람 수: " + ++peopleCount);
        } else {
            System.out.println("매장 내 사람이 꽉 찼습니다." + name + "가 못들어옴");
        }
    }

    public void exit(String name) {
        if (peopleCount > 0) {
            System.out.println(name + "이 매장에서 나갔습니다. 매장 내 사람 수: " + --peopleCount);
        } else {
            System.out.println("매장 내 사람이 없습니다.");
        }
    }

    public synchronized void buy() {
        while (productCount >= MAX_PRODUCT) {
            System.out.println("재고가 가득찼습니다. 재고: " + productCount);
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
        }
        productCount++;
        System.out.println("생산자로부터 물건을 납품받았습니다. 재고: " + productCount);
        notifyAll(); 
    }

    public synchronized void sell(String name) {
        while (productCount <= 0) {
            System.out.println(name + "가 사려고 했지만 재고가 없습니다. 재고: " + productCount);

            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
        }
        productCount--;
        System.out.println(name + "에게 물건을 팔았습니다. 재고: " + productCount);

        // 재고 떨어지면 바로 채워넣기
        if(productCount == 0){
            buy();
        }
    }
}