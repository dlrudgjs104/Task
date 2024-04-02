package simplefbp;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class OutputNode extends Node {
    String ip;
    int port;

    static final int QUEUESIZE = 10;

    OutputNode(String name){
        super(name);
    }
 
    OutputNode(String name, String ip, int port) {
        super(name);
        this.ip = ip;
        this.port = port;
    }

    public void operate() {
        try {
            Socket socket = new Socket(ip, port);
            logger.trace("{} 연결 완료", name);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            // 객체를 직렬화하여 전송
            oos.writeObject(pipe.pollMessage().getData());
            oos.flush();

        } catch (UnknownHostException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public Pipe getPipe(){
        return pipe;
    }


}
