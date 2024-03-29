package simplefbp;

public class AddNode extends FunctionNode {

    public AddNode(String name) {
        super(name);
    }

    @Override
    public void processMessages() {
        // 모든 입력 포트로부터 메시지를 받았는지 확인
        boolean allInputsReceived = true;
        for (Pipe inputPipe : inputPipes) {
            if (inputPipe == null) {
                allInputsReceived = false;
                break;
            }
        }

        // 모든 입력 포트로부터 메시지를 받았다면 계산 수행
        if (allInputsReceived) {
            int sum = 0;
            for (Pipe inputPipe : inputPipes) {
                int inputValue = (int) inputPipe.pollMessage().getData();
                sum += inputValue;
            }

            // 모든 출력 포트로 결과 전달
            for (Pipe outputPipe : outputPipes) {
                outputPipe.addMessage(new intMessage(sum));
            }
        }
    }
}
