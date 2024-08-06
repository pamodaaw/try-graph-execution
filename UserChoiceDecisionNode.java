import java.util.ArrayList;
import java.util.List;

// UserChoiceDecisionNode class implementation
class UserChoiceDecisionNode implements Node {

    private String name;
    private List<Node> nextNodes; // For branching paths
    private Node selectedNode; // For selected path

    public UserChoiceDecisionNode(String name) {
        this.name = name;
        this.nextNodes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addNextNode(Node nextNode) {
        nextNodes.add(nextNode);
    }

    public List<Node> getNextNodes() {
        return nextNodes;
    }

    public Node getNextNode() {
        return selectedNode;
    }

    @Override
    public ProcessResult execute(List<String> providedData) {
        // Decision logic
        System.out.println("Decision point: " + name);

        boolean nodeSelected = false;

        if (providedData != null && !providedData.isEmpty()) {
            // Get the node which matches the user's choice.
            for (Node nextNode : nextNodes) {
                if (nextNode.getName().equals(providedData.get(0))) {
                    selectedNode = nextNode;
                    nodeSelected = true;
                    break;
                }
            }
        }
        if (nodeSelected) {
            return new ProcessResult("COMPLETE", null);
        } else {
            return new ProcessResult("ERROR", null);
        }

    }

    @Override
    public ProcessResult declareInputData() {

        if (selectedNode != null) {
            System.out.println("DN selected node exists");

            return null;
        }
        List<String> options = new ArrayList<>();
        for (Node nextNode : nextNodes) {
            System.out.println("DN adding options: " + nextNode.getName());
            options.add(nextNode.getName());
        }
        return new ProcessResult("SELECTION_REQUIRED", options);
    }
}
