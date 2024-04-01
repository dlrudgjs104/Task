package simplefbp;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Node {
    String id;
    String name;
    Pipe pipe = new Pipe(10);
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    Node(String name){
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public synchronized void notifyOwn(){
        notify();
    }

}
