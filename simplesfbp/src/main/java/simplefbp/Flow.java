package simplefbp;

import java.util.ArrayList;
import java.util.List;

public class Flow {
    List<Node> nodes;
    List<Node> producerNodes;
    List<Node> consumerNodes;
    List<Pipe> pipes;

    Flow() {
        this.nodes = new ArrayList<>();
        this.producerNodes = new ArrayList<>();
        this.consumerNodes = new ArrayList<>();
        this.pipes = new ArrayList<>();
    }

    public void addNode(Node node) {
        if (node instanceof ConsoleInNode) {
            producerNodes.add(node);
        } else if (node instanceof TerminalOutNode) {
            consumerNodes.add(node);
        } else {
            nodes.add(node);
        }
    }

    public void removeNode(Node node, int index) {
        if (node instanceof ConsoleInNode) {
            producerNodes.remove(index);
        } else if (node instanceof TerminalOutNode) {
            consumerNodes.remove(index);
        } else {
            nodes.remove(index);
        }
    }

    public void addPipe(Pipe pipe) {
        pipes.add(pipe);
    }

    public Node getlNode(Node node, int index) {
        if (node instanceof ConsoleInNode) {
            return producerNodes.get(index);
        } else if (node instanceof TerminalOutNode) {
            return consumerNodes.get(index);
        } else {
            return nodes.get(index);
        }
    }

    // 파이프를 생성하고 노드들을 연결하는 메서드
    public void connectNodes(Node sourceNode, Node targetNode, Pipe pipe) {
        sourceNode.outputPipeConnect(pipe);
        targetNode.inputPipeConnect(pipe);
        pipes.add(pipe);
    }

    // Flow를 시작하는 메서드
    public void start() {
        for (Node node : nodes) {
            // 각 노드를 시작
            node.start();
        }
    }

    public int nodesCount() {
        return nodes.size();
    }

    public int producerNodesCount() {
        return producerNodes.size();
    }

    public int consumerNodesCount() {
        return consumerNodes.size();
    }

    public int pipesCount() {
        return pipes.size();
    }
}
