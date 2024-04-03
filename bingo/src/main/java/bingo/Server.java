package bingo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    private Thread sendThread;
    private Thread receiveThread;
    private static final int PORT = 1234;
    private static final int MAX_CLIENTS = 2;
    private static final int COUNTDOWN = 3;
    private int clientCount = 0;
    private String line = "";
    private List<Socket> sockets = new ArrayList<>();
    private String[][] player = new String[2][25];
    private Bingo bingo = new Bingo();

    public Server() {
        
    }

    public void serverStart(){
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("연결된 호스트 IP: " + socket.getInetAddress().getHostAddress());
                
                sockets.add(socket);
                clientCount++;
                System.out.println("현재 접속한 클라이언트 수: " + clientCount);

                sendThread = new Thread(() -> send(socket, "서버와 연결되었습니다. 현재 클라이언트 수: " + clientCount));
                sendThread.start();

                receiveThread = new Thread(() -> receive(socket));
                receiveThread.start();

                if(clientCount >= MAX_CLIENTS){
                    System.out.println("게임이 3초 뒤에 시작 됩니다.");
                    sendToAll("게임이 3초 뒤에 시작 됩니다.");

                    try {
                        for(int i = COUNTDOWN; i > 0; i--){
                            System.out.println(i + "초");
                            sendToAll(i + "초");
                            Thread.sleep(1000);
                        }
                        
                        System.out.println("각 플레이어는 다음과 같이 1~25의 숫자를 모두 입력해 주세요 ex) 4 5 2 1 ... :");
                        sendToAll("각 플레이어는 다음과 같이 1~25의 숫자를 모두 입력해 주세요 ex) 4 5 2 1 ... :");

                        
                        
                        
                    } catch (InterruptedException e) {
                        System.err.println("게임 카운트 다운중 오류: " + e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("에러: " + e.getMessage());
        }
    
    }

    public void send(Socket socket, String sendText) {
        try {
            socket.getOutputStream().write((sendText + "\n").getBytes());
        } catch (Exception e) {
            System.err.println("에러: " + e.getMessage());
        }
    }

    public void receive(Socket socket) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while ((line = input.readLine()) != null) {
                System.out.println("받은 내용: " + line);
                bingo.getBorad(line);
            }

            socket.close();
            System.exit(0);

        } catch (Exception e) {
            System.err.println("에러: " + e.getMessage());
        }
    }

    public void sendToAll(String message){
        for(Socket socket : sockets){
            send(socket, message);
        }
    }
}
