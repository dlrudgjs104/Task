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
        ConsoleInNode v3InputNode = new ConsoleInNode("v3InputNode");
        ConsoleInNode v4InputNode = new ConsoleInNode("v4InputNode");

        AddNode addNode1 = new AddNode("addNode1");
        AddNode addNode2 = new AddNode("addNode2");
        DivideNode divideNode = new DivideNode("divideNode");
        MultiplyNode multiplyNode = new MultiplyNode("multiplyNode");

        TerminalOutNode r1OutputNode = new TerminalOutNode("r1OutputNode");
        TerminalOutNode r2OutputNode = new TerminalOutNode("r2OutputNode");
        TerminalOutNode r3OutputNode = new TerminalOutNode("r3OutputNode");

        // Pipe 생성
        Pipe pipe1 = new Pipe(5);
        Pipe pipe2 = new Pipe(5);
        Pipe pipe3 = new Pipe(5);
        Pipe pipe4 = new Pipe(5);
        Pipe pipe5 = new Pipe(5);
        Pipe pipe6 = new Pipe(5);
        Pipe pipe7 = new Pipe(5);
        Pipe pipe8 = new Pipe(5);

        // Node와 Pipe 연결
        v1InputNode.inputPipeConnect(pipe1);
        v2InputNode.inputPipeConnect(pipe2);
        v3InputNode.inputPipeConnect(pipe3);
        v4InputNode.inputPipeConnect(pipe4);

        addNode1.inputPipeConnect(pipe1);
        addNode1.inputPipeConnect(pipe2);
        addNode1.outputPipeConnect(pipe5);

        addNode2.inputPipeConnect(pipe5);
        addNode2.inputPipeConnect(pipe3);
        addNode2.outputPipeConnect(pipe6);

        divideNode.inputPipeConnect(pipe6);
        divideNode.inputPipeConnect(pipe4);
        divideNode.outputPipeConnect(pipe7);

        multiplyNode.inputPipeConnect(pipe5);
        multiplyNode.inputPipeConnect(pipe7);
        multiplyNode.outputPipeConnect(pipe8);

        r1OutputNode.outputPipeConnect(pipe5);
        r2OutputNode.outputPipeConnect(pipe7);
        r3OutputNode.outputPipeConnect(pipe8);

        // Flow 시작
        flow.start();

    }
}