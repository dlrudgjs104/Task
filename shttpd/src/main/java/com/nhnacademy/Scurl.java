package com.nhnacademy;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Scurl {
    private String[] args;
    private String host = "";
    private Socket socket;
    private PrintStream writer;
    public static final String CRLF = "\r\n";

    public Scurl() {

    }

    public void operate() {
        try {
            socket = new Socket("localhost", 80);
            System.out.println("서버에 연결 되었습니다.");

            allReceiver();

            writer = new PrintStream(socket.getOutputStream(), true);
            // writer.printf("POST Readme.txt HTTP/1.1" + CRLF);

            // 파일 경로 지정
            File file = new File("./.vscode/launch.json");

            

            // 파일 내용을 전송
            try {
                Socket fileSocket = new Socket("localhost", 80);
                System.out.println("파일 전송을 위한 연결이 되었습니다.");
                OutputStream outputStream = fileSocket.getOutputStream();
                BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(file));

                PrintWriter writer2 = new PrintWriter(fileSocket.getOutputStream(), true);
                writer2.printf("DELETE Readme.txt HTTP/1.1" + CRLF);

                try {Thread.sleep(1000);} catch (InterruptedException e) {}
                
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                System.out.println("파일 전송이 완료 되었습니다.");
                fileInputStream.close();
                fileSocket.close();
                System.out.println("파일 전송을 위한 연결이 해제 되었습니다.");
                
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        

    }

    public void allReceiver() {
        Thread receiver = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                String responseLine;
                while ((responseLine = reader.readLine()) != null) {
                    System.out.println(responseLine);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });

        receiver.start();
    }

}
