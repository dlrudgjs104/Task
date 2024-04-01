package simplefbp;

public class TerminalOutNode extends ActiveNode implements Consumer {
    Message message;
    Thread thread;

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
        thread = new Thread(this::perform);
        thread.start();
    }

    @Override
    public synchronized void perform() {
        while (pipe.queueCheck()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }

        logger.trace("Terminal Out: {}", (int) pipe.pollMessage().getData());
        thread.interrupt();
    }
}
