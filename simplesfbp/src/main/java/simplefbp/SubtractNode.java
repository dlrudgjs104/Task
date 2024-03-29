package simplefbp;

public class SubtractNode extends FunctionNode {

    public SubtractNode(String name) {
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
            int result = (int) inputPipes.get(0).pollMessage().getData();
            for (int i = 1; i < inputPipes.size(); i++) {
                result -= (int) inputPipes.get(i).pollMessage().getData();
            }

            // 모든 출력 포트로 결과 전달
            for (Pipe outputPipe : outputPipes) {
                outputPipe.addMessage(new intMessage(result));
            }
        }
    }
}
