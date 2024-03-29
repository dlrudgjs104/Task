package simplefbp;

import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsoleInNode extends ActiveNode implements Producer {
    Logger logger;
    Pipe inputPipe;
    Scanner scanner = new Scanner(System.in);
    OutputNode outputNode = new OutputNode("Output Node", 2);

    ConsoleInNode(String name) {
        super(name);

        logger = LogManager.getLogger(this.getClass().getSimpleName());
        start();
    }

    @Override
    public void inputPipeConnect(Pipe inputPipe) {
        this.inputPipe = inputPipe;
    }

    @Override
    public void start() {
        Thread thread = new Thread(this::perform);
        thread.start();
    }

    @Override
    public void perform() {
        System.out.printf("입력: ");

        if (scanner.hasNextInt()) {
            int num = scanner.nextInt();
            logger.trace("입력된 값은 정수 입니다.");
            outputNode.getPipe().addMessage(new intMessage(num));
        } else if (scanner.hasNext()) {
            String str = scanner.nextLine();
            logger.trace("입력된 값은 문자열 입니다.");
            outputNode.getPipe().addMessage(new StringMessage(str));
        } else {
            logger.trace("올바른 입력값이 아닙니다.");
        }
    }

}
