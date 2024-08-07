package node;

import executor.Executor;
import model.InputData;
import model.ProcessResult;
import model.RequiredData;
import node.Node;

// node.TaskExecutorNode.java
public class TaskExecutorNode implements Node {
    private String name;
    private Node nextNode; // For sequential traversal
    private Executor executor; // Reference to the executor.Executor

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
    public ProcessResult execute(InputData inputData) {
        // Delegate the execution logic to the executor.Executor
        System.out.println("Inside task node: " + name);
        if (executor != null) {
            RequiredData data = executor.process(inputData.getInputData());
            if (data != null && "USER_INPUT".equals(data.getInputType())) {
                return new ProcessResult("INCOMPLETE", data);
            }
            return new ProcessResult("COMPLETE");
        } else {
            return new ProcessResult("ERROR");
        }
    }

    @Override
    public RequiredData declareInputData() {

        if (executor != null) {
            return executor.declareRequiredData();
        }
        return null;
    }
}
