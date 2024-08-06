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
    public ProcessResult execute(List<String> providedData) {

        // Nothing to process.
        return declareInputData();
    }

    @Override
    public ProcessResult declareInputData() {

        List<String> requiredData = new ArrayList<>();
        for (Node referencedNode : referencedNodes) {
            ProcessResult result = referencedNode.declareInputData();
            if (result != null && result.getRequiredData() != null) {
                requiredData.addAll(result.getRequiredData());
            }
        }
        // Only declare the data required. So this node is complete.
        return new ProcessResult("COMPLETE", requiredData);
    }
}
