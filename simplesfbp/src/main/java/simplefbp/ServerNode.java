package simplefbp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerNode extends Node {
    int port;

    ServerNode(String name, int port) {
        super(name);
        this.port = port;
    }

    public void operate() {
        try (ServerSocket serverSocket = new ServerSocket(1234);) {
            logger.trace("서버시작");

            while (true) {
                Socket socket = serverSocket.accept();
                logger.trace("Client IP: {}, Client Port: {}", socket.getInetAddress().getHostAddress(),
                        socket.getPort());

                InputNode inputNode = new InputNode("Input Node", socket);
                inputNode.operate();

                pipe.addMessage(inputNode.pipe.pollMessage());


                OutputNode outputNode = new OutputNode("Output Node", "localhost", 1234);
                outputNode.pipe.addMessage(pipe.pollMessage());
                outputNode.operate();

            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    } 
}
