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

public class ServiceHandler implements Runnable {
    private Socket socket;
    private Shttpd shttpd;
    public BufferedReader reader;
    public PrintWriter writer;
    private DirectoryCheck directoryCheck;
    public static final String FILED_CONTENT_LENGTH = "content-length";
    public static final String CRLF = "\r\n";

    ServiceHandler(Socket socket, Shttpd shttpd) {
        this.socket = socket;
        this.shttpd = shttpd;
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
                System.out.println(requestLine);

                if (requestLine == null) {

                    break;
                }
                String[] fields = requestLine.split("\\s+", 3);

                if (fields.length != 3) {
                    throw new Exception();
                }

                response(fields[0], fields[1], fields[2]);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public void response(String command, String location, String version) {

        if (command.equals("GET")) {
            directoryList(location);
        }

        if (command.equals("POST")) {
            saveFile(location);

        } else {
            methodNotAllowed405();
        }

        if (command.equals("DELETE")) {
            deleteFile(location);
        }
    }

    public void directoryList(String location) {
        File currentDirectory = new File(".");
        File locationFile = new File(location);
        File[] files = currentDirectory.listFiles();

        if (files != null && location == null) {
            System.out.println("리스트 전송 실행");
            writer.printf("HTTP/1.1 200 OK" + CRLF);
            writer.printf("Content-Type: text/html" + CRLF);
            writer.printf("Content-Length: " + CRLF);
            writer.printf(CRLF);
            writer.printf("<html><head><title>Directory Listing</title></head><body><ul>" + CRLF);

            for (File file : files) {
                writer.printf("<li>" + file.getName() + "<li>" + CRLF);
            }
            writer.printf("<ul><body></html>" + CRLF);

        } else if (location != null && directoryCheck.directoryCheckRun(location)) {
            if (locationFile.exists()) {
                if (locationFile.canRead()) {
                    writer.printf("HTTP/1.1 200 OK" + CRLF);
                    writer.printf("Content-Type: text/html" + CRLF);
                    writer.printf(String.format("Content-Length: %s" + CRLF, readFile(location).length()));
                    writer.printf(CRLF);
                    writer.printf(readFile(location));
                } else {
                    System.out.println("파일을 읽을 수 없습니다.");
                    forbidden403();
                }

            } else {
                notFound404();
            }

        } else {
            forbidden403();
        }
    }

    public void noContent204(){
        writer.printf("HTTP/1.1 204 No Content" + CRLF);
        writer.printf(CRLF);
        writer.printf("Content-Type: text/html" + CRLF);
        writer.printf("Content-Length: " + CRLF);
        writer.printf(CRLF);
    }

    public void notFound404() {
        writer.printf("HTTP/1.1 404 NOT Found" + CRLF);
        writer.printf(CRLF);
        writer.printf("Content-Type: text/html" + CRLF);
        writer.printf("Content-Length: " + CRLF);
        writer.printf(CRLF);
    }

    public void forbidden403() {
        writer.printf("HTTP/1.1 403 Fobidden" + CRLF);
        writer.printf(CRLF);
        writer.printf("Content-Type: text/html" + CRLF);
        writer.printf("Content-Length: " + CRLF);
        writer.printf(CRLF);
    }

    public void methodNotAllowed405() {
        writer.printf("HTTP/1.1 405 Method Not Allowed" + CRLF);
        writer.printf(CRLF);
        writer.printf("Content-Type: text/html" + CRLF);
        writer.printf("Content-Length: " + CRLF);
        writer.printf(CRLF);
    }

    public void conflict409(){
        writer.printf("HTTP/1.1 409 Conflict" + CRLF);
        writer.printf(CRLF);
        writer.printf("Content-Type: text/html" + CRLF);
        writer.printf("Content-Length: " + CRLF);
        writer.printf(CRLF);
    }

    public void send(String line) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.printf(line);
        } catch (Exception e) {
            e.getStackTrace();
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
            conflict409();
        }
        else if (file.canWrite()) {
            System.out.println("파일에 쓸 수 있습니다.");
            try (BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream());
                    BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(filePath))) {

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
                writer.printf("HTTP/1.1 200 OK" + CRLF);

            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        else{
            System.out.println("파일에 쓸 수 없습니다.");
            forbidden403();
        }

    }

    public void deleteFile(String location){
        File file = new File(location);
        if (file.exists()){
            if(file.canWrite()){
                file.delete();
                System.out.println(location + "파일이 제거 되었습니다.");
                noContent204();
            }
            else{
                System.out.println(location + "해당 파일을 제거할 수 없습니다.");
                forbidden403();
            }
        }
        else{
            System.out.println(location + "해당 파일이 존재하지 않습니다.");
            noContent204();
        }

    }

}
