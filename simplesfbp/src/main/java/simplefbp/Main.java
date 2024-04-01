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

        ConsoleInNode inputNode;
        TerminalOutNode terminalOutNode = new TerminalOutNode("r1TerminalOutNode");
        FunctionNode functionNode = new FunctionNode("functnion Node", 3);

        while ( functionNode.inputPortCount-- > 0){
            inputNode = new ConsoleInNode("InputNode");

            // 메세지가 입력될 때까지 대기
            while (inputNode.pipe.getQueueLength() == 0){      
            }

            // 메세지를 Function Node에 전달
            functionNode.pipe.addMessage(inputNode.pipe.pollMessage());
        }

        // Function Node 작업 실행
        functionNode.operate();

        terminalOutNode.pipe.addMessage(functionNode.pipe.pollMessage());

        terminalOutNode.notifyOwn();

    }
}