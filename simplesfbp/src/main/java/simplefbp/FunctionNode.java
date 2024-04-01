package simplefbp;

public class FunctionNode extends Node implements Producer, Consumer {
    int inputPortCount = 2;
    int result;
    int a;
    int b;
    int c;
    int d;

    public FunctionNode(String name, int inputPortCount) {
        super(name);
        this.inputPortCount = inputPortCount;
        logger.trace("입력 포트: {}개", inputPortCount);
    }

    public void operate() {
        logger.trace("{} 시작", name);

        if (pipe.getQueueLength() == 2) {
            a = (int) pipe.pollMessage().getData();
            b = (int) pipe.pollMessage().getData();

            logger.trace("a: {}, b: {}", a, b);
            
            r1();
        }
        else if (pipe.getQueueLength() == 3) {
            a = (int) pipe.pollMessage().getData();
            b = (int) pipe.pollMessage().getData();
            c = (int) pipe.pollMessage().getData();
            
            logger.trace("a: {}, b: {}, c: {}", a, b, c);
            
            r2();
        } 
        else if (pipe.getQueueLength() == 4) {
            a = (int) pipe.pollMessage().getData();
            b = (int) pipe.pollMessage().getData();
            c = (int) pipe.pollMessage().getData();
            d = (int) pipe.pollMessage().getData();

            logger.trace("a: {}, b: {}, c: {}, d: {}", a, b, c, d);
            
            r3();
        }
        else{

        }

        Message message = new IntMessage(result);
        pipe.addMessage(message);
    }

    public int add(int a, int b) {
        return a + b;
    }

    public int substract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int division(int a, int b) {
        return a / b;
    }

    public void r1() {
        result = add(a, b);
        logger.trace("{} + {} = {}", a, b, result);
    }

    public void r2() {
        result = division(add(a, b), c);
        logger.trace("({} + {}) / {} = {}", a, b, c, result);
    }

    public void r3() {
        result = multiply(add(a, b), division(c, d));
        logger.trace("({} + {}) * ({} / {}) = {}", a, b, c, d, result);
    }

    @Override
    public void outputPipeConnect(Pipe outputPipe) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'outputPipeConnect'");
    }

    @Override
    public Pipe inputPipeConnect() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inputPipeConnect'");
    }

}
