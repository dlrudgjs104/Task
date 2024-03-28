package com.nhnacademy;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Shttpd {
    private int port;
    private Socket socket;
    public static final String FILED_CONTENT_LENGTH = "content-length";
    public static final String CRLF = "\r\n";
    public BufferedReader reader;
    public PrintWriter writer;
    private List<ServiceHandler> services = new ArrayList<>();

    public Shttpd(int port) {
        this.port = port;
    }

    public void shttpdRun() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("서버 시작");

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
