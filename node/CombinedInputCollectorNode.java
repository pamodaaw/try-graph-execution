package node;

import model.InputData;
import model.ProcessResult;
import model.RequiredData;

import java.util.ArrayList;
import java.util.List;

public class CombinedInputCollectorNode implements Node {

    private String name;
    private List<Node> referencedNodes; // For branching paths
    private Node nextNode; // For selected path

    public CombinedInputCollectorNode(String name) {
        this.name = name;
        this.referencedNodes = new ArrayList<>();
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public void addReferencedNode(Node referencedNode) {

        referencedNodes.add(referencedNode);
    }

    @Override
    public Node getNextNode() {

        return nextNode;
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public ProcessResult execute(InputData inputData) {

        ProcessResult result = new ProcessResult("COMPLETE");
        for (Node referencedNode : referencedNodes) {
            RequiredData dataRequired = referencedNode.declareInputData();
            if (dataRequired != null) {
                result.addInputData(dataRequired);
            }
        }
        // Only declare the data required. So this node is complete.
        return result;
    }

    @Override
    public RequiredData declareInputData() {

        return null;
    }
}
