package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private int port;
    private String line = "";
    private ServerSocket serverSocket;
    private Socket socket;
    private Thread sendThread;
    private Thread receiveThread;
    private Scanner scanner = new Scanner(System.in);

    Server(int port) {
        this.port = port;

        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            System.out.println("Connected: " + socket.getInetAddress().getHostAddress());

            sendThread = new Thread(() -> send());
            sendThread.start();

            receiveThread = new Thread(() -> receive());
            receiveThread.start();

        } catch (IOException e) {
            System.err.println("에러: " + e.getMessage());
        }

    }

    public void send() {
        try {
            while (line != null){
                line = scanner.nextLine();
                socket.getOutputStream().write((line + "\n").getBytes());
            }

            socket.close();
            System.exit(0);

        } catch (Exception e) {
            System.err.println("에러: " + e.getMessage());
        }
    }

    public void receive() {
        while (!Thread.currentThread().isInterrupted()) {

            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while ((line = input.readLine()) != null) {
                    System.out.println("받은 내용: " + line);
                }

                socket.close();
                System.exit(0);

            } catch (Exception e) {
                System.err.println("에러: " + e.getMessage());
            }

        }
    }
}
