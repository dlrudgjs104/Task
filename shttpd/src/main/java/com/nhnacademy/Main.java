package com.nhnacademy;

public class Main {
    public static void main(String[] args) {
        ApachCommandCli cli = new ApachCommandCli(args);
            cli.cliRun();
    }
}


class Test{
    public static void main(String[] args) {
        Scurl scurl = new Scurl();
            scurl.operate();
    }
}  