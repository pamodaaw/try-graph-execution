package node;

import model.Element;
import model.InputData;
import model.ProcessResult;
import model.RequiredData;

import java.util.ArrayList;
import java.util.List;

// node.UserChoiceDecisionNode class implementation
public class UserChoiceDecisionNode implements Node {

    private String name;
    private final List<Node> nextNodes; // For branching paths
    private Node selectedNode; // For selected path

    public UserChoiceDecisionNode(String name) {
        this.name = name;
        this.nextNodes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setNextNode(Node nextNode) {
        nextNodes.add(nextNode);
    }

    public List<Node> getNextNodes() {
        return nextNodes;
    }

    public Node getNextNode() {
        return selectedNode;
    }

    @Override
    public ProcessResult execute(InputData inputData) {
        // Decision logic
        boolean nodeSelected = false;

        if (inputData != null && inputData.getInputData() != null && !inputData.getInputData().isEmpty()) {

            System.out.println("User input available. Start processing.");
            for (Node nextNode : nextNodes) {
                if (nextNode instanceof TaskExecutorNode) {
                    String executorName = ((TaskExecutorNode) nextNode).getExecutor().getName();
                    if (inputData.getInputData().get("USER_CHOICE").equals(executorName)) {
                        selectedNode = nextNode;
                        nodeSelected = true;
                        break;
                    }
                }
            }
        }
        if (nodeSelected) {
            System.out.println("User choice decision node " + name + " selected node " + selectedNode.getName());
            return new ProcessResult("COMPLETE");
        } else {
            System.out.println("User choice decision node " + name + " is incomplete. Need to make a choice.");
            return new ProcessResult("INCOMPLETE", declareInputData());
        }
    }

    @Override
    public RequiredData declareInputData() {

        if (selectedNode != null) {
            return null;
        }
        RequiredData input = new RequiredData("USER_CHOICE");
        input.setNodeName(name);
        int count = 0;
        for (Node nextNode : nextNodes) {
            if (nextNode instanceof TaskExecutorNode) {
                Element element = new Element("OPTION", ((TaskExecutorNode) nextNode).getExecutor().getName(),
                                              "STRING", ++count);
                input.addRequiredData(element);            }

        }
        return input;
    }
}
