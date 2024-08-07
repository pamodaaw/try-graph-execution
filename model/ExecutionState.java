package model;// model.ExecutionState.java
import model.ProcessResult;
import node.Node;

import java.util.ArrayList;
import java.util.List;

public class ExecutionState {
    private Node currentNode;
    private List<ProcessResult> results = new ArrayList<>();

    public ExecutionState(Node currentNode, ProcessResult result) {
        this.currentNode = currentNode;
        results.add(result);
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    public List<ProcessResult> getResults() {

        return results;
    }

    public void addResult(ProcessResult result) {

        results.add(result);
    }
}
