package simplefbp;

public class MultiplyNode extends FunctionNode {

    public MultiplyNode(String name) {
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
            int result = 1;
            for (Pipe inputPipe : inputPipes) {
                result *= (int) inputPipe.pollMessage().getData();
            }

            // 모든 출력 포트로 결과 전달
            for (Pipe outputPipe : outputPipes) {
                outputPipe.addMessage(new intMessage(result));
            }
        }
    }
}
