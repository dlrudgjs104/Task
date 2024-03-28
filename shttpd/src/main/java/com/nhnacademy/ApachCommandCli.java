package com.nhnacademy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


public class ApachCommandCli {
    private String[] args;

    public ApachCommandCli(String[] args) {
        this.args = args;
    }

    public void cliRun() {
        Options options = new Options();
        int port = 80;

        options.addOption("p", true, "port");
  
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine line = parser.parse(options, args);

            if (line.hasOption("p")){
                port = Integer.parseInt(line.getOptionValue("p"));
                Shttpd shttpd = new Shttpd(port);
                shttpd.shttpdRun();
            }

        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
    }

}
