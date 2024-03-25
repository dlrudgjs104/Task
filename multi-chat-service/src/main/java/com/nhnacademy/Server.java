package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private Scanner scanner = new Scanner(System.in);
    private int clientId = 1;
    private List<ClientHandler> clients = new ArrayList<>();
    private int port;
    private DataHandler settingData;
    private DataHandler logData;
    private int id = 1;
    private boolean monitorValue = false;

    Server(int port) {
        this.port = port;
    }

    public void operate() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("서버 시작");

            settingData = new DataHandler("./src/main/java/com/nhnacademy/setting.json");
            settingData.operate();

            logData = new DataHandler("./src/main/java/com/nhnacademy/logData.json");
            logData.operate();

            Thread consoleThread = new Thread(() -> console());
            consoleThread.start();

            while (true) {
                socket = serverSocket.accept();
                System.out.println("Connected: " + socket.getInetAddress().getHostAddress());

                ClientHandler clientHandler = new ClientHandler(socket, clientId++ + "", this);
                clients.add(clientHandler);

                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.getStackTrace();
            settingData.saveToFile();
            logData.saveToFile();
        }

        // 서버가 종료될때 데이터를 저장
        settingData.saveToFile();
        logData.saveToFile();
    }

    public void console() {
        try {
            String line = "";
            String[] lines;

            while (line != null) {
                try {
                    line = scanner.nextLine();
                    lines = line.split(",");

                    for (int i = 0; i < lines.length; i++) {
                        lines[i] = lines[i].trim();
                    }

                    // 입력 ex) console,deny add 123
                    if (lines[0].equals("console")) {
                        consoleCommand(lines[1]);
                    } else {
                        JSONObject message = new JSONObject();
                        message.put("target_id", lines[0]);
                        message.put("type", "message");
                        message.put("message", lines[1]);
                        message.put("client_id", "server");

                        if (lines[0].equals("a")) { // 모든 클라이언트에게 메세지 전송
                            for (ClientHandler clientHandler : clients) {
                                clientHandler.send(message.toString());
                            }
                        } else {
                            for (int i = 0; i < clients.size(); i++) { // 특정 클라이언트에게 메세지 전송
                                if (clients.get(i).getClientId().equals(lines[0].trim())) {
                                    clients.get(i).send(message.toString());
                                }
                            }
                        }
                    }
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

    public void consoleCommand(String line) {
        if (line.length() >= 11) {
            if (line.substring(0, 11).equals("client_list")) {
                System.out.println(clientList());
            }
        }

        if (line.length() >= 8) {
            if (line.substring(0, 8).equals("deny add")) {
                denyAdd(line.substring(9));
            } else if (line.substring(0, 8).equals("deny del")) {
                denyDel(line.substring(9));
            } else if (line.substring(0, 8).equals("send_off")) {
                sendOff(line.substring(9));
            } else if (line.substring(0, 8).equals("log show")) {
                logShow(line.substring(9));
            }
        }

        if (line.equals("monitor on")) {
            monitorOn();
            System.out.println("사용자간 메세지 감시를 설정합니다.");
        }

        if (line.equals("monitor off")) {
            monitorOff();
            System.out.println("사용자간 메세지 감시 설정을 해제합니다.");
        }
    }

    public String clientList() {
        String list = "[Access Client List]";

        for (int i = 0; i < clients.size(); i++) {
            list += "\n" + clients.get(i).getClientId();
        }

        return list;
    }

    public void denyAdd(String line) {
        JSONArray dataArray = (JSONArray) settingData.get("deny_list");

        for (int i = 0; i < dataArray.length(); i++) {
            if (dataArray.get(i).equals(line)) {
                System.out.println("이미 존재하는 대상 입니다.");
                return;
            }
        }

        dataArray.put(line);
        settingData.put("deny_list", dataArray);
        settingData.saveToFile();
        System.out.println(" 대상을 접속 차단 리스트에 추가하였습니다.");
    }

    public void denyDel(String line) {
        JSONArray dataArray = (JSONArray) settingData.get("deny_list");
        if (dataArray.length() == 0) {
            System.out.println("deny_list가 비어있습니다.");
        } else {
            for (int i = 0; i < dataArray.length(); i++) {
                if (dataArray.get(i).equals(line)) {
                    dataArray.remove(i);
                } else {
                    System.out.println("해당하는 대상이 없습니다.");
                }
            }
            settingData.put("deny_list", dataArray);
            settingData.saveToFile();
            System.out.println("대상을 접속 차단 리스트에서 제거 하였습니다.");
        }

    }

    public void monitorOn() {
        monitorValue = true;
    }

    public void monitorOff() {
        monitorValue = false;
    }

    public boolean getMonitorValue(){
        return monitorValue;
    }

    public void sendOff(String line) {
        for (int i = 0; i < clients.size(); i++) {
            if (line.equals(clients.get(i).getClientId())) {
                try {
                    System.out.println("클라이언트 " + clients.get(i).getClientId() + "을 강퇴하였습니다.");

                    JSONObject message = new JSONObject();
                    message.put("id", id++);
                    message.put("type", "send_off");
                    message.put("target_id", clients.get(i).getClientId());
                    message.put("message", "서버에 의해 강퇴당하였습니다.");

                    clients.get(i).sendToTarget(message.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public void logShow(String line) {
        String[] lines = line.split(" ");
        JSONArray logArray = (JSONArray) logData.get("log");

        for(int i = 0; i < lines.length; i++){
            lines[i] = lines[i].trim();
        }

        System.out.println("[Log List]");

        if (lines.length == 0){
            for (int i = 0; i < 10; i++) {
                System.out.println(logArray.get(i).toString());
            }
        }
        else if (lines.length == 1){
            for (int i = 0; i < Integer.parseInt(lines[0]); i++) {
                System.out.println(logArray.get(i).toString());
            }     
        }
        else if (lines.length == 2){
            for (int i = Integer.parseInt(lines[0]); i < Integer.parseInt(lines[0]) + Integer.parseInt(lines[1]); i++) {
                System.out.println(logArray.get(i).toString());
            }

        }
    }

    public void receive() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;

            while ((line = input.readLine()) != null) {
                System.out.println("받은 내용: " + line);
            }

            socket.close();
            System.exit(0);

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public List<ClientHandler> getClients() {
        return clients;
    }

    public DataHandler getSettingData() {
        return settingData;
    }

    public DataHandler getLogData() {
        return logData;
    }

}
