package simplefbp;

import java.util.ArrayList;
import java.util.List;

public class FunctionNode extends Node implements Producer, Consumer {
    List<Pipe> inputPipes;
    List<Pipe> outputPipes;

    public FunctionNode(String name) {
        super(name);
        inputPipes = new ArrayList<>();
        outputPipes = new ArrayList<>();
    }

    // 입력 파이프 연결
    @Override
    public void inputPipeConnect(Pipe inputPipe) {
        inputPipes.add(inputPipe);
    }

    // 출력 파이프 연결
    @Override
    public void outputPipeConnect(Pipe outputPipe) {
        outputPipes.add(outputPipe);
    }

    // 메시지 처리
    public void processMessages() {
        // 각 입력 파이프에서 메시지를 읽어와 가공 후 출력 파이프로 전달
        for (Pipe inputPipe : inputPipes) {
            Message message = inputPipe.pollMessage();
            // 메시지 가공 로직 수행
            Message processedMessage = processMessage(message);
            // 출력 파이프로 메시지 전달
            for (Pipe outputPipe : outputPipes) {
                outputPipe.addMessage(processedMessage);
            }
        }
    }

    // 메시지 가공 로직
    public Message processMessage(Message message) {
        // 메시지를 가공하는 로직을 구현
        // 여기서는 간단히 입력 메시지를 그대로 반환하는 예시
        return message;
    }
}
