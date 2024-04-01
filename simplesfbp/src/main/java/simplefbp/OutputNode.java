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
            System.out.println("연결완료");

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            // 객체를 직렬화하여 전송
            oos.writeObject(this.pipe.pollMessage().getData());
            oos.flush();

        } catch (UnknownHostException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void operate2(){

    }

    public Pipe getPipe(){
        return pipe;
    }


}
