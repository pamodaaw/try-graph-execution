// MainTest.java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class MainTest {
    public static void main(String[] args) {

//        case1();
//        case2();
        case3();

    }

    private static void case1() {

        System.out.println("Case 1: Sequential execution");
        Executor executorA = new SimpleExecutor("Task A logic", null);
        Executor executorB = new SimpleExecutor("Task B logic", null);
        Executor executorC = new SimpleExecutor("Task C logic", null);

        TaskExecutorNode nodeA = new TaskExecutorNode("nodeA", executorA);
        TaskExecutorNode nodeB = new TaskExecutorNode("nodeB", executorB);
        TaskExecutorNode nodeC = new TaskExecutorNode("nodeC", executorC);


        // Define the flow of the graph
        RegSequence regSequence = new RegSequence();

        regSequence.addNode(nodeA);
        regSequence.addNode(nodeB);
        regSequence.addNode(nodeC);

        regSequence.addNextNode("nodeA", nodeB);
        regSequence.addNextNode("nodeB", nodeC);

        executeSequence(regSequence);

        System.out.println("Case 1: Sequential execution completed");
    }

    private static void case2() {

        System.out.println("Case 2: Sequential execution with second task requiring input");
        Executor executorA = new SimpleExecutor("Task A logic", null);
        Executor executorB = new SimpleExecutor("Task B logic", Arrays.asList("InputDataB1", "InputDataB2"));
        Executor executorC = new SimpleExecutor("Task C logic", null);

        TaskExecutorNode nodeA = new TaskExecutorNode("nodeA", executorA);
        TaskExecutorNode nodeB = new TaskExecutorNode("nodeB", executorB);
        TaskExecutorNode nodeC = new TaskExecutorNode("nodeC", executorC);

        // Define the flow of the graph
        RegSequence regSequence = new RegSequence();

        regSequence.addNode(nodeA);
        regSequence.addNode(nodeB);
        regSequence.addNode(nodeC);

        regSequence.addNextNode("nodeA", nodeB);
        regSequence.addNextNode("nodeB", nodeC);

        executeSequence(regSequence);

        System.out.println("Case 2: Completed");
    }

    private static void case3() {

        System.out.println("Case 3: Decision node in the middle");
        Executor executorA = new SimpleExecutor("Task A logic", Arrays.asList("InputDataA1", "InputDataA2"));
        Executor executorB = new SimpleExecutor("Task B logic", null);
        Executor executorC = new SimpleExecutor("Task C logic", null);
        Executor executorD = new SimpleExecutor("Task D logic", null);

        TaskExecutorNode nodeA = new TaskExecutorNode("ExN1", executorA);
        TaskExecutorNode nodeB = new TaskExecutorNode("ExN2", executorB);
        TaskExecutorNode nodeC = new TaskExecutorNode("ExN3", executorC);
        TaskExecutorNode nodeD = new TaskExecutorNode("ExN4", executorD);

        UserChoiceDecisionNode nodeD1 = new UserChoiceDecisionNode("DN1");

        CombinedInputCollectorNode nodeCC1 = new CombinedInputCollectorNode("CC1");
        nodeCC1.addReferencedNode(nodeA);
        nodeCC1.addReferencedNode(nodeD1);

        // Define the flow of the graph
        RegSequence regSequence = new RegSequence();

        regSequence.addNode(nodeCC1);
        regSequence.addNode(nodeA);
        regSequence.addNode(nodeD1);
        regSequence.addNode(nodeB);
        regSequence.addNode(nodeC);
        regSequence.addNode(nodeD);

        regSequence.addNextNode("CC1", nodeA);
        regSequence.addNextNode("ExN1", nodeD1);
        regSequence.addNextNode("DN1", nodeB);
        regSequence.addNextNode("DN1", nodeC);
        regSequence.addNextNode("ExN2", nodeD);
        regSequence.addNextNode("ExN3", nodeD);

        executeSequence(regSequence);

        System.out.println("Case 3: Completed");
    }

    private static List<String> getUserInput(List<String> requiredData) {
        // Simulate user input collection
        List<String> inputs = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        for (String data : requiredData) {
            System.out.print("Enter " + data + ": ");
            inputs.add(scanner.nextLine());
        }

        return inputs;
    }

    private static List<String> getUserChoice(List<String> requiredData) {
        // Simulate user input collection
        List<String> inputs = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // Create the prompt message
        StringBuilder promptMessage = new StringBuilder("Choose ");
        for (int i = 0; i < requiredData.size(); i++) {
            promptMessage.append(requiredData.get(i));
            if (i < requiredData.size() - 1) {
                promptMessage.append(" OR ");
            }
        }
        promptMessage.append(": ");

        // Display the prompt and get user input
        System.out.print(promptMessage.toString());
        String userInput = scanner.nextLine();
        inputs.add(userInput);

        return inputs;
    }

    private static void executeSequence(RegSequence seq) {

        // Traverse and execute the graph
        boolean isCompleted = false;
        List<String> inputs = new ArrayList<>();

        // Traverse and execute the graph
        while (!isCompleted) {
            ExecutionState state = seq.execute(inputs);
            for (ProcessResult result : state.getResults()) {
                if ("SUCCESSFUL".equals(result.getStatus())) {
                    isCompleted = true;
                    break;
                }
                inputs.clear();
                if (Objects.equals(result.getStatus(), "INPUT_REQUIRED")) {
                    inputs = getUserInput(result.getRequiredData());
                }
                if (Objects.equals(result.getStatus(), "SELECTION_REQUIRED")) {
                    inputs = getUserChoice(result.getRequiredData());
                }
            }
        }
    }
}
