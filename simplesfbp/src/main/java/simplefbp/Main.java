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
        ClientNode clientNode = new ClientNode("ClientNode");
        clientNode.operate();

        
        
    }
}

class FunctionNodeTest{
    public static void main(String[] args) {

        ConsoleInNode consoleInNode;
        TerminalOutNode terminalOutNode = new TerminalOutNode("r1TerminalOutNode");
        FunctionNode functionNode = new FunctionNode("functnion Node", 3);

        while (functionNode.inputPortCount-- > 0){
            consoleInNode = new ConsoleInNode("ConsoleInNode");

            // 메세지가 입력될 때까지 대기
            while (consoleInNode.pipe.getQueueLength() == 0){      
            }

            // 메세지를 Function Node에 전달
            functionNode.pipe.addMessage(consoleInNode.pipe.pollMessage());
        }

        // Function Node 작업 실행
        functionNode.operate();

        // 출력 터미널로 값 전달
        terminalOutNode.pipe.addMessage(functionNode.pipe.pollMessage());

        terminalOutNode.notifyOwn();

    }
}