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

    public Executor getExecutor() {

        return executor;
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
        RequiredData data;
        if (executor != null) {
            if (inputData != null) {
                data = executor.process(inputData.getInputData());
            } else {
                data = executor.process(null);
            }

            if (data != null && "USER_INPUT".equals(data.getInputType())) {
                data.setNodeName(name);
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
            RequiredData data = executor.declareRequiredData();
            data.setNodeName(name);
            return data;
        }
        return null;
    }
}
