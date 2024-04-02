package simplefbp;


public class ClientNode extends Node{
    ConsoleInNode consoleInNode;
    OutputNode outputNode;

    ClientNode(String name){
        super(name);

        consoleInNode = new ConsoleInNode("Console Node", this);
        TerminalOutNode terminalOutNode = new TerminalOutNode("Terminal Out Node");
        outputNode = new OutputNode("Output Node", "localhost", 1234);
    }

    public synchronized void operate(){
        //logger.trace("{} 시작", name);
        while(true){
            // 메세지가 입력될 때까지 대기
            while (consoleInNode.pipe.getQueueLength() == 0){
                // try {
                //     wait();
                // } catch (InterruptedException e) {
                //     System.err.println(e.getMessage());
                // }
            }

            // 메세지를 Function Node에 전달
            outputNode.pipe.addMessage(consoleInNode.pipe.pollMessage());
            outputNode.operate();
        }
    }
}
