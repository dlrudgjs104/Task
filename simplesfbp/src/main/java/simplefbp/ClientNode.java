package simplefbp;

import java.net.Socket;

public class ClientNode extends Node{
    Socket socket;
    String ip;
    int port;

    ClientNode(String name, String ip, int port){
        super(name);
        this.ip = ip;
        this.port = port;
    }

    public void operate(){
        
    }
}
