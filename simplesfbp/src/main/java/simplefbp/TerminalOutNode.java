package simplefbp;

public class TerminalOutNode extends ActiveNode implements Consumer {
    Pipe outputPipe;

    TerminalOutNode(String name) {
        super(name);
        start();
    }

    @Override
    public void outputPipeConnect(Pipe outputPipe) {
        this.outputPipe = outputPipe;
    }

    @Override
    public void start() {
        Thread thread = new Thread(this::perform);
        thread.start();
    }

    @Override
    public void perform() {
        while (true) {
            Message message = outputPipe.pollMessage();

            if (message != null) {
                System.out.println(message.getData());
            }
        }

    }
    

}
