package com.example.nhnacademy.producer_consumer_solution;

public class Test {
    public static void main(String[] args) {
        Store store = new Store();
   
        Thread producer = new Thread(new Producer("납품자", store));
        producer.start();
        
        String[] people = {"man1", "man2", "man3", "man4", "man5", "man6", "man7", "man8", "man9"};
        
        for(String man : people){
            Thread consumer = new Thread(new Consumer(man, store));
            consumer.start();
        }
        

    }
}
