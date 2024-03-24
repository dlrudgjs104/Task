package com.nhnacademy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientHandler implements Runnable {
    private Socket socket;
    private String clientId;
    private Server server;
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    public ClientHandler(Socket socket, String clientId, Server server) {
        this.socket = socket;
        this.clientId = clientId;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            // 클라이언트에게 ID 전송
            send(setMessage("setId", clientId, "Your client ID = " + clientId));

            // 클라이언트로부터 메시지 수신
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;

            while ((line = in.readLine()) != null) {
                addLog(line);
                monitoring(server.getMonitorValue(), line);

                messageCheck(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void messageCheck(String line) {
        JSONObject message = new JSONObject(line);
        printForm(message);

        // 접속 허용 여부 결정
        if (message.get("type").equals("connect")) {
            if (!denyCheck()) {
                message.put("response", "ok");
                System.out.println("클라이언트 id: " + message.get("id") + "의 접속을 허용하였습니다.");
            } else {
                message.put("response", "deny");
                System.out.println("클라이언트 id: " + message.get("id") + "의 접속을 차단하였습니다.");
            }

            send(message.toString());
        }

        // 메세지 전송
        else if (message.get("type").equals("message")) {
            sendToTarget(line);
        }

        // 클라이언트가 접속자 명단을 요청한 경우
        else if (message.get("type").equals("client_list")) {
            send(setMessage("cmd", clientId, server.clientList()));
        }
    }

    public void printForm(JSONObject message) {
        System.out.println(String.format("ID[%s]:%s", message.get("client_id"), message.get("message").toString()));
    }

    public String setMessage(String type, String targetId, String line) {
        JSONObject message = new JSONObject();

        message.put("type", type);
        message.put("client_id", "server");
        message.put("target_id", targetId);
        message.put("message", line);

        return message.toString();
    }

    public String getClientId() {
        return clientId;
    }

    public Socket getSocket() {
        return socket;
    }

    public void send(String line) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(line);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void sendToTarget(String line) {
        try {
            JSONObject message = new JSONObject(line);
            List<ClientHandler> clients = this.server.getClients();

            boolean findTarget = false;
            for (ClientHandler targetClient : clients) {
                if (message.get("target_id").equals(targetClient.getClientId())) {
                    findTarget = true;
                    targetClient.send(line);
                }
            }

            if (!findTarget) {
                send(setMessage("message", clientId,
                        String.format("[%s]%s", message.get("target_id"), "클라이언트를 찾을 수 없습니다.")));
            }

        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    public boolean denyCheck() {
        JSONArray denyArray = (JSONArray) server.getSettingData().get("deny_list");

        for (int i = 0; i < denyArray.length(); i++) {
            if (denyArray.get(i).equals(clientId)) {
                System.out.println("차단된 대상이 접근을 시도하였습니다.");
                return true;
            }
        }
        return false;
    }

    public void addLog(String log) {
        JSONObject modifyObject = new JSONObject();
        JSONArray logArray = (JSONArray) server.getLogData().get("log");
        LocalDateTime modifyTime = LocalDateTime.now();

        modifyObject.put("content", log);
        modifyObject.put("time", modifyTime);

        logArray.put(modifyObject);
        server.getLogData().put("log", logArray);
        server.getLogData().saveToFile();
    }

    public void monitoring(boolean value, String line){
        if(value){
            logger.trace("Message trace: {}", line);
        }
        
    }
}