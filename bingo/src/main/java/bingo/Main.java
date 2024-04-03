package bingo;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.serverStart();
    }
}

class TestClient1{
    public static void main(String[] args) {
        Client client1 = new Client();
        client1.clientStart();
    }
}

class TestClient2{
    public static void main(String[] args) {
        Client client2 = new Client();
        client2.clientStart();
    }
}