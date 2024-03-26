package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Scurl {
    private String[] args;
    private Socket socket;
    private String host = "";
    private String location = "";

    public Scurl(String[] args) {
        this.args = args;

        String[] temp;
        String[] temp2;

        // 주소 추출
        for(int i = 0; i < args.length; i++){
            if(args[i].startsWith("http://")){
                temp = args[i].split("//");
                temp2 = temp[1].split("/");
                host = temp2[0];
                location = "/" + temp2[1];
            }
        }   
    }

    public void operate() {
        Options options = new Options();
        int port = 80;
        String version = "HTTP/1.1";
        String command = "GET";
        String header = "";
        String postBody = "";
        String filePath = "";

        options.addOption("v", false, "Option v");
        options.addOption("H", true, "Option H");
        options.addOption("d", true, "Option d");
        options.addOption("X", true, "Option X");
        options.addOption("L", false, "Option L");
        options.addOption("F", true, "Option F");

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine line = parser.parse(options, args);

            if (line.hasOption("X")) {
                command = line.getOptionValue("X");
                if (command.equals("POST")) {
                    command = "POST";
                    location = "/post";
                }
            }

            if (line.hasOption("H")) {
                header = line.getOptionValue("H");
            }

            if (line.hasOption("d")) {
                command = "POST";
                location = "/post";
                postBody = line.getOptionValue("d");
            }

            if (line.hasOption("F")) {
                location = "/post";
                filePath = line.getOptionValue("F");
                filePath = filePath.substring("upload=".length());
            }

            if (line.getArgs().length >= 0) {
                socket = new Socket(host, port);

                PrintStream writer = new PrintStream(socket.getOutputStream());

                // 호스트 정보만 있을 경우
                if (!line.hasOption("v") && !line.hasOption("X") && !line.hasOption("F")) {
                    System.out.println("호스트 정보만 있을 경우");

                    bodyReceiver();
                    writer.printf(String.format("%s %s %s\r\n", command, location, version));
                    writer.printf(String.format("Host: %s\r\n", host));
                    writer.print("\r\n");
                }

                // Method명을 명시적으로 GET 요청
                if (!line.hasOption("v") && line.hasOption("X") && !line.hasOption("H") && !line.hasOption("d")) {
                    System.out.println("Method명을 명시적으로 GET 요청");

                    bodyReceiver();
                    writer.printf(String.format("%s %s %s\r\n", command, location, version));
                    writer.printf(String.format("Host: %s\r\n", host));
                    writer.print("\r\n");
                }

                // Request/Response header 출력
                if (line.hasOption("v") && line.hasOption("X") && !line.hasOption("H") && !line.hasOption("d")) {
                    System.out.println("Request/Response header 출력");

                    allReceiver();
                    writer.printf(String.format("%s %s %s\r\n", command, location, version));
                    writer.printf(String.format("Host: %s\r\n", host));
                    writer.print("\r\n");
                }

                // X-Custom-Header: NA를 request header에 추가
                if (line.hasOption("v") && line.hasOption("H") && !line.hasOption("d")) {
                    System.out.println("X-Custom-Header: NA를 request header에 추가");
                    allReceiver();
                    writer.printf(String.format("%s %s %s\r\n", command, location, version));
                    writer.printf(String.format("Host: %s\r\n", host));
                    writer.printf(String.format("%s\r\n", header));
                    writer.print("\r\n");
                }

                // POST 요청
                if (line.hasOption("d")) {
                    System.out.println("POST 요청");
                    allReceiver();

                    writer.printf(String.format("%s %s %s\r\n", command, location, version));
                    writer.printf(String.format("Host: %s\r\n", host));
                    writer.printf(String.format("%s\r\n", "Content-Length: " + postBody.length()));
                    writer.printf(String.format("%s\r\n", header));
                    writer.printf(String.format("\r\n%s", postBody));
                    writer.print("\r\n");
                }

                // 30x 응답 처리
                if (line.hasOption("L")) {
                    location = "/status/302";
                    System.out.println("30x 응답 처리");

                    int redirectionCount = 0;
                    while (redirectionCount < 5) {

                        socket = new Socket(host, port);
                        writer = new PrintStream(socket.getOutputStream());

                        writer.printf(String.format("%s %s %s\r\n", command, location, version));
                        writer.printf(String.format("Host: %s\r\n", host));
                        writer.print("\r\n");

                        location = redirectReceiver();

                        if (location == null) {
                            break;
                        }

                        redirectionCount++;

                        socket.close();
                    }
                }

                // 파일 전송
                if (line.hasOption("F")) {
                    System.out.println("파일 전송");

                }

            } else {
                System.err.println("URL이 필요합니다.");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public void allReceiver() {
        Thread receiver = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()))) {

                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    System.out.println(inputLine);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });

        receiver.start();
    }

    public void headerReceiver() {
        Thread receiver = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()))) {

                String inputLine;
                if ((inputLine = reader.readLine()) != null) {
                }

                while ((inputLine = reader.readLine()) != null && !inputLine.isEmpty()) {
                    System.out.println(inputLine);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });

        receiver.start();
    }

    public void bodyReceiver() {
        Thread receiver = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()))) {

                String inputLine;
                if ((inputLine = reader.readLine()) != null) {
                }

                while ((inputLine = reader.readLine()) != null && !inputLine.isEmpty()) {
                }

                while ((inputLine = reader.readLine()) != null) {
                    System.out.println(inputLine);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });

        receiver.start();
    }

    public String redirectReceiver() {
        String location = "";
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()))) {

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);

                if (inputLine.startsWith("location: ")) {
                    location = inputLine.substring("location: ".length()).trim();
                }
            }
            return location;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return location;
        }
    }
}
