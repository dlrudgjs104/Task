package simplefbp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerNode extends Node {
    int port;
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

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

                OutputNode outputNode = new OutputNode("Output Node", "localhost", 1234);
                outputNode.operate();

            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
