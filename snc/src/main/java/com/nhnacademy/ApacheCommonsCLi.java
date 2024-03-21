package com.nhnacademy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class ApacheCommonsCLi {
    private String[] args;
    private int portValue;
    private String ipValue;

    public ApacheCommonsCLi(String[] args) {
        this.args = args;

        Options options = new Options();

        Option serverOption = Option.builder("l")
                .desc("ServerMode")
                .build();

        Option ip = Option.builder("i")
                .longOpt("ip")
                .hasArg()
                .desc("IP")
                .build();

        Option port = Option.builder("p")
                .longOpt("port")
                .hasArg()
                .desc("Port")
                .build();

        options.addOption(serverOption);
        options.addOption(ip);
        options.addOption(port);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption(port.getOpt())) {
                portValue = Integer.parseInt(cmd.getOptionValue(port.getOpt()));
            }

            if(cmd.hasOption((ip.getOpt()))){
                ipValue = cmd.getOptionValue(ip.getOpt());
            }

            
            if (cmd.hasOption(serverOption.getOpt())) {   // 서버모드
                Server server = new Server(portValue);
            }
            else{   // 클라이언트 모드
                Client client = new Client(ipValue, portValue);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
