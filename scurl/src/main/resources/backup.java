package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Scurl {
    private String scurlValue;
    private String[] args;

    public Scurl(String[] args) {
        this.args = args;
    }

    public void operate() {
       Options options = new Options();
        String version = "HTTP/1.1";
        String command = "GET";
        String location = "/get";
        int port = 80;

        Option httpoption = Option.builder("s")
                .longOpt("scurl")
                .hasArg()
                .argName("command")
                .desc("(HTTP)")
                .build();

        options.addOption(httpoption);

        options.addOption("v", false, "Option v");
        options.addOption("H", true, "Option H");
        options.addOption("d", true, "Option d");
        options.addOption("X", true, "Option X");
        options.addOption("L", false, "Option L");
        options.addOption("F", true, "Option F");     

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine line = parser.parse(options, args);

            if (line.hasOption("s")) {
                command = line.getOptionValue("s");
            }

            if (line.getArgs().length > 0) {
                String host = line.getArgs()[0];
                System.out.println(host);

                Socket socket = new Socket(host, port);

                PrintStream writer = new PrintStream(socket.getOutputStream());

                writer.printf(String.format("%s %s %s\r\n", command, location, version));
                writer.printf(String.format("Host: %s\r\n", host));
                writer.print("\r\n");

                Thread receiver = new Thread(() -> {
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()))) {

                        String inputLine;
                        while ((inputLine = reader.readLine()) != null) {
                            System.out.println(inputLine);
                        }
                    } catch (IOException e) {
                    }
                });

                receiver.start();
            } else {
                System.err.println("URL이 필요합니다.");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


    }
}
