package node;

import model.InputData;
import model.ProcessResult;
import model.RequiredData;

// node.Node interface
public interface Node {
    Node getNextNode();
    String getName();

    void setNextNode(Node nextNode);

    ProcessResult execute(InputData inputs); // Placeholder for node-specific actions

    RequiredData declareInputData(); // Placeholder for declaring required data
}
