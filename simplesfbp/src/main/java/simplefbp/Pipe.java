package simplefbp;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class Pipe{
    String id;
    Queue<Message> messageQueue = new LinkedList<>();
    Queue<Message> priorityQueue = new LinkedList<>();
    int queueSize;
    boolean priorityCheck;

    Pipe(int queueSize){
        this.id = UUID.randomUUID().toString();
        this.queueSize = queueSize;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public synchronized void addMessage(Message message){
        if(messageQueue.size() < queueSize){
            if(priorityCheck){
                priorityQueue.add(message);
            }
            else{
                messageQueue.add(message);
            }
        }
        else{
            System.out.println("메세지 용량 초과");
        }
        
        
    }

    public synchronized Message pollMessage(){
        if(!priorityQueue.isEmpty()){
            return priorityQueue.poll();
        }
        else{
            return messageQueue.poll();
        }   
    }

    public int getQueueLength(){
        return messageQueue.size() + priorityQueue.size();
    }

    public boolean queueCheck(){
        return messageQueue.isEmpty() && priorityQueue.isEmpty();
    }
 
}
