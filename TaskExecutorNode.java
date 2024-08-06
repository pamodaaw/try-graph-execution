import java.util.ArrayList;
import java.util.List;

// TaskExecutorNode.java
public class TaskExecutorNode implements Node {
    private String name;
    private Node nextNode; // For sequential traversal
    private Executor executor; // Reference to the Executor

    public TaskExecutorNode(String name, Executor executor) {
        this.name = name;
        this.executor = executor;
    }

    public String getName() {
        return name;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public Node getNextNode() {
        return nextNode;
    }

    @Override
    public ProcessResult execute(List<String> providedData) {
        // Delegate the execution logic to the Executor
        if (executor != null) {
            return executor.process(providedData);
        } else {
            return new ProcessResult("ERROR", null);
        }
    }

    @Override
    public ProcessResult declareInputData() {

        if (executor != null) {
            return new ProcessResult("INPUT_REQUIRED", executor.declareRequiredData());
        } else {
            return new ProcessResult("ERROR", null);
        }
    }
}
