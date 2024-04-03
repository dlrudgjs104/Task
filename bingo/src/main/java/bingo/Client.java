package bingo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String line = "";
    private Socket socket;
    private Thread sendThread;
    private Thread receiveThread;
    private static final String IP = "localhost";
    private static final int PORT = 1234;
    Scanner scanner = new Scanner(System.in);

    public Client() {

    }

    public void clientStart() {
        try {
            socket = new Socket(IP, PORT);

        } catch (Exception e) {
            System.err.println("에러: " + e.getMessage());
        }

        sendThread = new Thread(() -> send());
        sendThread.start();

        receiveThread = new Thread(() -> receive());
        receiveThread.start();
    }

    public void send() {
        try {
            System.out.println("서버에 연결되었습니다.");

            while (line != null) {
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
        BufferedReader input;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while ((line = input.readLine()) != null) {
                System.out.println("전송받은 내용: " + line);
            }
            System.out.println("연결을 종료합니다.");

            socket.close();
            System.exit(0);

        } catch (Exception e) {
            System.err.println("에러: " + e.getMessage());
        }
    }
}
