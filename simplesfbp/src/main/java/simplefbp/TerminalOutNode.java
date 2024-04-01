package simplefbp;

public class TerminalOutNode extends ActiveNode implements Consumer {
    Message message;

    TerminalOutNode(String name) {
        super(name);
        start();
    }

    @Override
    public void outputPipeConnect(Pipe outputPipe) {
        pipe = outputPipe;
    }

    @Override
    public void start() {
        Thread thread = new Thread(this::perform);
        thread.start();
    }

    @Override
    public void perform() {
        while (true) {

            if (message != null) {
                System.out.println(message.getData());
            }
        }

    }
    

}
