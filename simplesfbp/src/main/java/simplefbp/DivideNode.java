package simplefbp;

public class DivideNode extends FunctionNode {

    public DivideNode(String name) {
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
            int dividend = (int) inputPipes.get(0).pollMessage().getData();
            int result = dividend;
            for (int i = 1; i < inputPipes.size(); i++) {
                int divisor = (int) inputPipes.get(i).pollMessage().getData();
                // 0으로 나누는 경우 오류 방지
                if (divisor != 0) {
                    result /= divisor;
                } else {
                    // 오류 처리: 0으로 나누는 경우 결과를 0으로 설정
                    result = 0;
                    break;
                }
            }

            // 모든 출력 포트로 결과 전달
            for (Pipe outputPipe : outputPipes) {
                outputPipe.addMessage(new intMessage(result));
            }
        }
    }
}
