package simplefbp;

import java.util.ArrayList;
import java.util.List;

public class Flow {
    List<Node> nodes;
    List<ConsoleInNode> producerNodes;
    List<TerminalOutNode> consumerNodes;
    List<Pipe> pipes;

    Flow() {
        this.nodes = new ArrayList<>();
        this.producerNodes = new ArrayList<>();
        this.consumerNodes = new ArrayList<>();
        this.pipes = new ArrayList<>();
    }

    public void addNode(Node node) {
        if (node instanceof ConsoleInNode) {
            producerNodes.add((ConsoleInNode) node);
        } else if (node instanceof TerminalOutNode) {
            consumerNodes.add((TerminalOutNode) node);
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

    // Flow를 시작하는 메서드
    public void start() {
        for (ConsoleInNode node : producerNodes) {
            // 각 노드를 시작
            node.start();
        }

        for (TerminalOutNode node : consumerNodes) {
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
