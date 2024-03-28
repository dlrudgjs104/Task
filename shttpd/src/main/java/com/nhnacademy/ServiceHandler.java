package com.nhnacademy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceHandler implements Runnable {
    private Socket socket;
    private Shttpd shttpd;
    public BufferedReader reader;
    public PrintWriter writer;
    private DirectoryCheck directoryCheck;
    private static final String CRLF = "\r\n";
    private static final String VERSION = "HTTP/1.1";
    Logger logger;

    ServiceHandler(Socket socket, Shttpd shttpd) {
        this.socket = socket;
        this.shttpd = shttpd;
        logger = LogManager.getLogger(this.getClass().getSimpleName());
        directoryCheck = new DirectoryCheck();

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                String requestLine = reader.readLine();
                logger.trace("< {}", requestLine.replaceAll("\r\n", "\r\n< "));

                if (requestLine == null) {
                    break;
                }
                String[] fields = requestLine.split("\\s+", 3);

                if (fields.length != 3) {
                    throw new Exception();
                }

                requestCheck(fields[0], fields[1], fields[2]);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public void requestCheck(String command, String location, String version) {

        if (command.equals("GET")) {
            directoryList(location);
        }

        else if (command.equals("POST")) {
            if (isMultipartFormDataRequest()){
                saveFile(location);
            }
            else{
                methodNotAllowed405();
            }
        }
        else if (command.equals("DELETE")) {
            deleteFile(location);
        }
    }

    public void directoryList(String location) {
        File locationFile = new File(location);

        Response response;

        File currentDirectory = new File(".");
        File[] files = currentDirectory.listFiles();

        if (files != null && location.equals("/")) {

            response = new Response(VERSION, "200", "OK");

            for (File file : files) {
                response.setBody(file.getName());
            }

            send(response.getMessage());

        } else if (location != null && directoryCheck.directoryCheckRun(location)) {
            if (locationFile.exists()) {
                if (locationFile.canRead()) {
                    response = new Response(VERSION, "200", "OK");
                    response.setBody(readFile(location));
                    send(response.getMessage());
                } else {
                    forbidden403();
                }

            } else {
                notFound404();
            }

        } else {
            forbidden403();
        }
    }

    public String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        String line;

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(filePath));

            // 파일 내용 읽기
            try {
                while ((line = fileReader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

        return content.toString();
    }

    public void saveFile(String filePath) {
        File file = new File(filePath);

        if(file.exists()){
            logger.trace("동일한 이름을 가진 파일이 이미 존재합니다.");

            conflict409();
        }
        else if (true /*file.canWrite()*/) {
            try (BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream());
                    BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(filePath))) {

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
                System.out.println("파일을 성공적으로 저장하였습니다.");

                writer.printf("HTTP/1.1 200 OK" + CRLF);

            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        else{
            logger.trace("파일에 쓸 수 없습니다.");
            forbidden403();
        }

    }

    public void deleteFile(String location){
        File file = new File(location);
        if (file.exists()){
            if(file.canWrite()){
                file.delete();
                logger.trace("{} 파일이 제거 되었습니다.", location);
                noContent204();
            }
            else{
                logger.trace("{} 해당 파일을 제거할 수 없습니다.", location);
                forbidden403();
            }
        }
        else{
            logger.trace("{} 해당 파일이 존재하지 않습니다.", location);
            noContent204();
        }

    }

    public void noContent204(){
        Response response = new Response(VERSION, "200", "OK");
        send(response.getMessage());
    }

    public void notFound404() {
        Response response = new Response(VERSION, "404", "NOT Found");
        send(response.getMessage());
    }

    public void forbidden403() {
        Response response = new Response(VERSION, "403", "Fobidden");
        send(response.getMessage());
    }

    public void methodNotAllowed405() {
        Response response = new Response(VERSION, "405", "Method Not Allowed");
        send(response.getMessage());
    }

    public void conflict409(){
        Response response = new Response(VERSION, "409", "Conflict");
        send(response.getMessage());
    }

    public void send(String line) {
        writer.printf(line);
        logger.trace("> {}", line.replaceAll("\r\n", "\r\n> "));
    }

    // request 메세지를 제대로 인식하고 구별하는 클래스가 필요
    public boolean isMultipartFormDataRequest(){

        return true;
    }


}
