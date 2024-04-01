package simplefbp;

public class FunctionNode extends Node implements Producer, Consumer {
    int result;

    public FunctionNode(String name) {
        super(name);

    }




    public int add(int a, int b){
        return a + b;
    }

    public int substract(int a, int b){
        return a - b;
    }

    public int multiply(int a, int b){
        return a * b;
    }

    public int division(int a, int b){
        return a / b;
    }

    public void r1(int a, int b){
        result = add(a, b);
    }

    public void r2(int a, int b, int c){
        result = division(add(a,b), c);
    }

    public void r3(int a, int b, int c, int d){
        result = multiply(add(a,b), division(c, d));
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
