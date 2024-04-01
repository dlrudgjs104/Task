package simplefbp;

import java.util.UUID;

public abstract class Node {
    String id;
    String name;
    Pipe pipe = new Pipe(10);

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

}
