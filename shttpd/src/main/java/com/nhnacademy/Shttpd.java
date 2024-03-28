package com.nhnacademy;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Shttpd extends Thread {
    int port;
    Socket socket;
    static final String FILED_CONTENT_LENGTH = "content-length";
    static final String CRLF = "\r\n";
    BufferedReader reader;
    PrintWriter writer;
    List<ServiceHandler> services = new ArrayList<>();
    Logger logger;

    public Shttpd(int port) {
        this.port = port;
        logger = LogManager.getLogger(this.getClass().getSimpleName());
    }

    @Override
    public void run(){
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.trace("서버 시작 : {}", port);

            while (true) {
                socket = serverSocket.accept();
                System.out.println("Connected: " + socket.getInetAddress().getHostAddress());

                ServiceHandler serviceHandler = new ServiceHandler(socket, this);
                services.add(serviceHandler);

                new Thread(serviceHandler).start();        
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
