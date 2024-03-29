package simplefbp;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Message {
    String id;
    LocalDateTime currentTime;
    Object data;

    Message(){
        this.id = UUID.randomUUID().toString();
        currentTime = LocalDateTime.now();
    }

    public String getId(){
        return id;
    }

    public LocalDateTime getCurrentTime(){
        return currentTime;
    }

    public Object getData(){
        return data;
    }

}
