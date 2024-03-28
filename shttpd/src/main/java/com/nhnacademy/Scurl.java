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

            test2();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        
    }

    // GET / 요청시에 현재 폴더 내부 목록을 html 로 응답한다.
    public void test1(){
        writer.printf("GET / HTTP/1.1" + CRLF);

    }
    
     // GET /file-path 요청에 응답한다.
    public void test2(){
        writer.printf("GET pom.xml HTTP/1.1" + CRLF);

    }

    // multipart/form-data 파일 업로드를 구현한다.
    // 원래는 요청메세지에 요청 정보랑 파일의 정보를 한번에 담아서 전송해야 하나의 소켓으로 한번에 결과 처리가 되는데 지금은 메세지와 업로드할 파일의 정보를 따로 보내다 보니 2개의 소켓을 이용하게 됨
    // 그래서 Thread.sleep을 통해 기다렸다가 파일 정보를 전송하는데 타이밍이 안맞을 경우 파일 내용이 제대로 저장이 안되기도 함
    public void test3(){

        // 파일 경로 지정
        File file = new File("./.vscode/launch.json");

        // 파일 내용을 전송
        try {
            Socket fileSocket = new Socket("localhost", 80);
            System.out.println("파일 전송을 위한 연결이 되었습니다.");
            OutputStream outputStream = fileSocket.getOutputStream();
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(file));

            PrintWriter writer2 = new PrintWriter(fileSocket.getOutputStream(), true);
            writer2.printf("POST ./src/main/Readme.txt HTTP/1.1" + CRLF);

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
        
    }

    // DELETE를 구현
    public void test4(){
        writer.printf("DELETE src/main/Readme.txt HTTP/1.1" + CRLF);
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
