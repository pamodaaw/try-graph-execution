// RegSequence.java
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class RegSequence {

    private List<Node> nodes; // Keep track of all nodes
    private ExecutionState currentState;

    public RegSequence() {
        this.nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addNextNode(String nodeName, Node nextNode) {
        Node currentNode = findNode(nodeName);
        if (currentNode != null) {
            if (currentNode instanceof TaskExecutorNode) {
                ((TaskExecutorNode) currentNode).setNextNode(nextNode);
            } else if (currentNode instanceof UserChoiceDecisionNode) {
                ((UserChoiceDecisionNode) currentNode).addNextNode(nextNode);
            }
        }
    }

    private Node findNode(String nodeName) {
        for (Node node : nodes) {
            if (node.getName().equals(nodeName)) {
                return node;
            }
        }
        return null;
    }

    public ExecutionState execute(List<String> providedData) {
        Node current;

        if (currentState == null) {
            // Start from the first node
            if (nodes.isEmpty()) return null;
            current = nodes.get(0);
        } else {
            // Resume from the current state
            current = currentState.getCurrentNode();

            // Optionally use providedData to update the state of the system if needed
            // This can include setting values, updating the system state, etc.
        }

        while (current != null) {
            ProcessResult result = current.execute(providedData);

            if (!"COMPLETE".equals(result.getStatus())) {
                // Input is required
                currentState = new ExecutionState(current, result);
                return currentState;
            }

            if ("COMPLETE".equals(result.getStatus()) &&
                    result.getRequiredData() != null && !result.getRequiredData().isEmpty()) {
                // Input is required but the node execution is complete.
                current = current.getNextNode();
                currentState = new ExecutionState(current, result);
                return currentState;
            }
            current = current.getNextNode();
        }

        // Clear the state if we reach the end of the execution
        currentState = null;
        return new ExecutionState(null, new ProcessResult("SUCCESSFUL", null)); // Execution completed
    }
}
