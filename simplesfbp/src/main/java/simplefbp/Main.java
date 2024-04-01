package simplefbp;

public class Main {
    public static void main(String[] args) {

        ServerNode serverNode = new ServerNode("ServerNode", 1234);
        Thread thread1 = new Thread( () ->{
            serverNode.operate();
        });
        
        thread1.start();
        
        
    }
    
}

class Test{
    public static void main(String[] args) {
        ClientNode clientNode = new ClientNode("ClientNode", "localhost", 1234);

        ConsoleInNode consoleNode = new ConsoleInNode("Console Node");

        TerminalOutNode terminalOutNode = new TerminalOutNode("Terminal Out Node");

        OutputNode outputNode = new OutputNode("outputNode", "localhost", 1234);

        Flow flow = new Flow();

        Thread thread2 = new Thread(() ->{
            clientNode.operate();
            outputNode.operate();
        });

        
        thread2.start();
    }
}

class FunctionNodeTest{
    public static void main(String[] args) {
        // Flow 생성
        Flow flow = new Flow();

        // Node 생성
        ConsoleInNode v1InputNode = new ConsoleInNode("v1InputNode");
        ConsoleInNode v2InputNode = new ConsoleInNode("v2InputNode");
        OutputNode outputNode = new OutputNode("outputNode", 2);
        TerminalOutNode r1TerminalOutNode = new TerminalOutNode("r1TerminalOutNode");


        flow.addNode(v1InputNode);
        flow.addNode(v2InputNode);
        flow.addNode(outputNode);
        flow.addNode(r1TerminalOutNode);

        flow.addPipe(outputNode.getPipe());


        // Flow 시작
        flow.start();

    }
}