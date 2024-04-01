package simplefbp;

import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsoleInNode extends ActiveNode implements Producer {
    Scanner scanner = new Scanner(System.in);
    Message message;
    Thread thread;

    ConsoleInNode(String name) {
        super(name);
        start();
    }

    @Override
    public Pipe inputPipeConnect() {
        return pipe;
    }

    @Override
    public void start() {
        thread = new Thread(this::perform);
        thread.start();
    }

    @Override
    public void perform() {
        logger.trace("{} 시작", name);
        System.out.printf("입력: ");

        if (scanner.hasNextInt()) {
            int num = scanner.nextInt();
            logger.trace("입력된 값은 정수 입니다.");
            message = new IntMessage(num);
            pipe.addMessage(message);
        } else if (scanner.hasNext()) {
            String str = scanner.nextLine();
            logger.trace("입력된 값은 문자열 입니다.");
            message = new StringMessage(str);
            pipe.addMessage(message);
        } else {
            logger.trace("올바른 입력값이 아닙니다.");
        }
        thread.interrupt();
    }

}
