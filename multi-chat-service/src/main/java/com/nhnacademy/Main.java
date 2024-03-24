package com.nhnacademy;

public class Main {
    public static void main(String[] args) {
        ApacheCommonsCLi cli = new ApacheCommonsCLi(args);
        cli.optionSetting();
    }
}

class ServerTest{   // 서버
    public static void main(String[] args) {
        Server server = new Server(1234);
        server.operate();
    }
}

class ClientTest {      // 접속이 허용된 사용자
    public static void main(String[] args) {
        Client client = new Client("localhost", 1234);
        client.operate();
    }
}

class ClientTest2 {     // 접속이 허용된 사용자
    public static void main(String[] args) {
        Client client2 = new Client("localhost", 1234);
        client2.operate();
    }
}   
class ClientTest3 {     // 접속이 차단된 사용자
    public static void main(String[] args) {
        Client client3 = new Client("localhost", 1234);
        client3.operate();
    }
}