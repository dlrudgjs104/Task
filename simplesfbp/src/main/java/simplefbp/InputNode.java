package simplefbp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class InputNode extends Node {
    Socket socket;

    InputNode(String name, Socket socket) {
        super(name);
        this.socket = socket;
        
    }

    public void operate() {
        // 클라이언트로부터 메시지 수신
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object receivedObject = ois.readObject();

            logger.trace("수신한 데이터: {}", receivedObject);
            
            pipe.addMessage(new StringMessage((String)receivedObject));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


}
