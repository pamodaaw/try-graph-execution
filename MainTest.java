// MainTest.java
import executor.AttributeCollector;
import executor.EmailOTPExecutor;
import executor.Executor;
import executor.PasswordOnboarder;
import model.Element;
import model.ExecutionState;
import model.InputData;
import model.ProcessResult;
import model.RegSequence;
import node.CombinedInputCollectorNode;
import node.TaskExecutorNode;
import node.UserChoiceDecisionNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainTest {
    public static void main(String[] args) {

//        case1();
//        case2();
        case3();

    }

//    private static void case1() {
//
//        System.out.println("Case 1: Sequential execution");
//        Executor executorA = new SimpleExecutor("Task A logic", null);
//        Executor executorB = new SimpleExecutor("Task B logic", null);
//        Executor executorC = new SimpleExecutor("Task C logic", null);
//
//        TaskExecutorNode nodeA = new TaskExecutorNode("nodeA", executorA);
//        TaskExecutorNode nodeB = new TaskExecutorNode("nodeB", executorB);
//        TaskExecutorNode nodeC = new TaskExecutorNode("nodeC", executorC);
//
//
//        // Define the flow of the graph
//        RegSequence regSequence = new RegSequence();
//
//        regSequence.addNode(nodeA);
//        regSequence.addNode(nodeB);
//        regSequence.addNode(nodeC);
//
//        regSequence.addNextNode("nodeA", nodeB);
//        regSequence.addNextNode("nodeB", nodeC);
//
//        executeSequence(regSequence);
//
//        System.out.println("Case 1: Sequential execution completed");
//    }
//
//    private static void case2() {
//
//        System.out.println("Case 2: Sequential execution with second task requiring input");
//        Executor executorA = new SimpleExecutor("Task A logic", null);
//        Executor executorB = new SimpleExecutor("Task B logic", Arrays.asList("InputDataB1", "InputDataB2"));
//        Executor executorC = new SimpleExecutor("Task C logic", null);
//
//        TaskExecutorNode nodeA = new TaskExecutorNode("nodeA", executorA);
//        TaskExecutorNode nodeB = new TaskExecutorNode("nodeB", executorB);
//        TaskExecutorNode nodeC = new TaskExecutorNode("nodeC", executorC);
//
//        // Define the flow of the graph
//        RegSequence regSequence = new RegSequence();
//
//        regSequence.addNode(nodeA);
//        regSequence.addNode(nodeB);
//        regSequence.addNode(nodeC);
//
//        regSequence.addNextNode("nodeA", nodeB);
//        regSequence.addNextNode("nodeB", nodeC);
//
//        executeSequence(regSequence);
//
//        System.out.println("Case 2: Completed");
//    }

    private static void case3() {

        System.out.println("Case 3: Decision node in the middle");
        AttributeCollector attrCollector1 = new AttributeCollector("AttributeCollector1");
        Element e1 = new Element("USER_ATTRIBUTE","emailaddress", "STRING", 1);
        attrCollector1.addRequiredData(e1);

        AttributeCollector attrCollector2 = new AttributeCollector("AttributeCollector2");
        Element e2 = new Element("USER_ATTRIBUTE","firstname", "STRING", 1);
        Element e3 = new Element("USER_ATTRIBUTE","dob", "DATE", 2);
        attrCollector2.addRequiredData(e2);
        attrCollector2.addRequiredData(e3);

        PasswordOnboarder pwdOnboard = new PasswordOnboarder();
        EmailOTPExecutor emailOTPExecutor = new EmailOTPExecutor();

        TaskExecutorNode node1 = new TaskExecutorNode("node1", attrCollector1);
        UserChoiceDecisionNode node2 = new UserChoiceDecisionNode("node2");
        TaskExecutorNode node3 = new TaskExecutorNode("node3", pwdOnboard);
        TaskExecutorNode node4 = new TaskExecutorNode("node4", emailOTPExecutor);
        TaskExecutorNode node5 = new TaskExecutorNode("node5", attrCollector2);


//        CombinedInputCollectorNode nodeCC1 = new CombinedInputCollectorNode("CC1");
//        nodeCC1.addReferencedNode(nodeA);
//        nodeCC1.addReferencedNode(nodeD1);

        // Define the flow of the graph
        RegSequence regSequence = new RegSequence();

//        regSequence.addNode(nodeCC1);
        regSequence.addNode(node1);
        regSequence.addNode(node2);
        regSequence.addNode(node3);
        regSequence.addNode(node4);
        regSequence.addNode(node5);

//        regSequence.addNextNode("CC1", nodeA);
        regSequence.addNextNode("node1", node2);
        regSequence.addNextNode("node2", node3);
        regSequence.addNextNode("node2", node4);
        regSequence.addNextNode("node3", node5);
        regSequence.addNextNode("node4", node5);

        executeSequence(regSequence);

        System.out.println("Case 3: Completed");
    }

    private static void getUserInput(List<String> inputs,List<String> requiredData) {
        // Simulate user input collection
        Scanner scanner = new Scanner(System.in);

        for (String data : requiredData) {
            System.out.print("Enter " + data + ": ");
            inputs.add(scanner.nextLine());
        }
    }

    private static void getUserChoice(List<String> inputs, List<String> requiredData) {

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
    }

    private static void executeSequence(RegSequence seq) {

        // Traverse and execute the graph
        boolean isCompleted = false;
        List<String> inputs = new ArrayList<>();
        int count = 0;

        // Traverse and execute the graph
        while (!isCompleted) {
            System.out.println("Executing sequence " + ++ count);
            ExecutionState state = seq.execute(inputs);
            for (ProcessResult result : state.getResults()) {
                if ("SUCCESSFUL".equals(result.getStatus())) {
                    isCompleted = true;
                    break;
                }
                inputs.clear();
                if ("INCOMPLETE".equals(result.getStatus())) {
                    System.out.println("node.Node returns incomplete.");
                    if (result.getInputDataList() != null && !result.getInputDataList().isEmpty()) {
                        for (InputData data : result.getInputDataList()) {
                            if ("USER_INPUT".equals(data.getInputType())) {
                                getUserInput(inputs, data.getRequiredData());
                            } else if ("USER_CHOICE".equals(data.getInputType())) {
                                getUserChoice(inputs, data.getRequiredData());
                            }
                            else {
                                System.out.println("Unknown input type");
                            }

                        }
                    }
                }
            }
        }
    }
}
