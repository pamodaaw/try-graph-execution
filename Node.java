import java.util.ArrayList;
import java.util.List;

// Node interface
interface Node {
    Node getNextNode();
    String getName();

    ProcessResult execute(List<String> providedData); // Placeholder for node-specific actions

    ProcessResult declareInputData(); // Placeholder for declaring required data
}
