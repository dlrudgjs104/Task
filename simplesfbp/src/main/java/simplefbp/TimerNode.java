package simplefbp;

public class TimerNode extends ActiveNode implements Producer {
    int interval;
    String message = "일정 간격 메세지 생성";
    Pipe inputPipe;

    TimerNode(String name) {
        super(name);
        start();
    }

    @Override
    public void start() {
        Thread thread = new Thread(this::perform);
        thread.start();
    }

    @Override
    public void perform() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }

            inputPipe.addMessage(new StringMessage(message));

        }
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void inputPipeConnect(Pipe inputPipe) {
        this.inputPipe = inputPipe;
    }

}
