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

        // pipe가 연결을 통해 메세지를 송수신 해주는 역할이면 consoleInNode에서 메세지를 보내고 그 데이터를 outputNode에서 pipe로 저장하고 데이터 처리
        // Producer는 메시지를 생산하여 파이프에 넣기 위한 기능들을 정의하고 있다.
        v1InputNode.start();
        v2InputNode.start();

        outputNode.getPipe().addMessage(v1InputNode.message);
        outputNode.getPipe().addMessage(v2InputNode.message);

        FunctionNode functionNode = new FunctionNode("functnion Node");

        flow.addNode(v1InputNode);
        flow.addNode(v2InputNode);
        flow.addNode(outputNode);
        flow.addNode(r1TerminalOutNode);

        flow.addPipe(outputNode.getPipe());


        // Flow 시작
        flow.start();

    }
}