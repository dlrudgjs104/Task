package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import org.json.JSONObject;

public class Client {
    private Socket socket;
    private String ip;
    private int port;
    private String clientId;
    private int id = 1;
    Scanner scanner = new Scanner(System.in);

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void operate() {
        try {
            socket = new Socket(ip, port);
            System.out.println("서버에 연결되었습니다.");
        } catch (Exception e) {
            e.getStackTrace();
        }

        Thread receiveThread = new Thread(() -> receive());
        receiveThread.start();
    }

    public void send() {
        try {
            JSONObject message = new JSONObject();
            String line = "";
            String[] lines;

            message.put("client_id", clientId);

            // 메세지 전송양식 : 타켓클라이언트id, 전송할 메세지 내용
            while (line != null) {
                try {
                    message.put("id", id++);

                    line = scanner.nextLine();
                    lines = line.split(",");

                    for (int i = 0; i < lines.length; i++) {
                        lines[i] = lines[i].trim();
                    }

                    if (lines[1].equals("client_list")) {
                        message.put("type", "client_list");
                    } else {
                        message.put("type", "message");
                    }

                    message.put("target_id", lines[0]);
                    message.put("message", lines[1]);

                    // 서버로 메세지 전송
                    PrintWriter out;
                    out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(message);

                } catch (Exception e) {
                    System.out.println("다시 입력해 주세요.");
                    continue;
                }

            }

            socket.close();
            System.exit(0);

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void send(String line) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(line);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void receive() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = "";

            while ((line = input.readLine()) != null) {
                messageCheck(line);
            }

            System.out.println("연결을 종료합니다.");

            socket.close();
            System.exit(0);

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void messageCheck(String line) {
        JSONObject message = new JSONObject(line);

        // 클라이언트 아이디 설정
        if (message.get("type").equals("setId")) {
            this.clientId = message.get("message").toString().substring(17);
            printForm(message);

            send(setMessage("connect", "server", "접속 요청"));
        }

        // 접속 요청 결과 확인
        else if (message.get("type").equals("connect")) {
            if (message.get("response").equals("ok")) {
                System.out.println("접속이 허용됬습니다.");

                Thread sendThread = new Thread(() -> send());
                sendThread.start();
            } else {
                System.out.println("접속이 차단됬습니다.");
                System.exit(0);
            }

        }

        // 클라이언트 접속 목록
        else if (message.get("type").equals("cmd")) {
            printForm(message);
        }

        // 메세지 수신
        else if (message.get("type").equals("message")) {
            printForm(message);
        }

        // 서버에 의한 강퇴
        else if (message.get("type").equals("send_off")) {
            System.out.println("서버에 의해 강퇴 당하였습니다.");
            try {
                socket.close();
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String setMessage(String type, String targetId, String line) {
        JSONObject message = new JSONObject();

        message.put("id", id);
        message.put("type", type);
        message.put("client_id", clientId);
        message.put("target_id", targetId);
        message.put("message", line);

        return message.toString();
    }

    public void printForm(JSONObject message) {
        System.out.println(String.format("ID[%s]:%s", message.get("client_id"), message.get("message").toString()));
    }

}
