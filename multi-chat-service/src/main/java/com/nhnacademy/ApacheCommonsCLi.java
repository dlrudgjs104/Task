package com.nhnacademy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class ApacheCommonsCLi {
    private int portValue;
    private String[] args;

    public ApacheCommonsCLi(String[] args) {
        this.args = args;
    }

    public void optionSetting() {
        Options options = new Options();

        Option clientOption = Option.builder("H")
                .longOpt("host")
                .hasArg()
                .desc("Client Mode")
                .build();

        options.addOption(clientOption);
        options.addOption("p","port",true, "Option p");

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("p")) {
                portValue = 1234;
                portValue = Integer.parseInt(cmd.getOptionValue("p"));
            }

            if (cmd.hasOption(clientOption.getOpt()) && cmd.hasOption("p")) { // 클라이언트 모드
                Client client = new Client("localhost", portValue);
                client.operate();
                
            } else if (cmd.hasOption("p")){ // 서버 모드
                Server server = new Server(portValue);
                server.operate();
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
