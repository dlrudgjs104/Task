package simplefbp;

public abstract class ActiveNode extends Node{

    ActiveNode(String name) {
        super(name);

    }

    void start(){}
    void initialize(){}
    void perform(){}
    void idle(){}
    void finalize1(){}
    void terminated(){}
}
