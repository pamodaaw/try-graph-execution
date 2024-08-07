package model;/*
 * Copyright (c) 2024, WSO2 LLC. (https://www.wso2.com) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import model.ProcessResult;
import node.Node;

import java.util.ArrayList;
import java.util.List;

public class RegSequence {

    private List<Node> nodes; // Keep track of all nodes
    private ExecutionState currentState;

    public RegSequence() {
        this.nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addNextNode(String nodeName, Node nextNode) {
        Node currentNode = findNode(nodeName);
        if (currentNode != null) {
            currentNode.setNextNode(nextNode);
        }
    }

    private Node findNode(String nodeName) {
        for (Node node : nodes) {
            if (node.getName().equals(nodeName)) {
                return node;
            }
        }
        return null;
    }

    public ExecutionState execute(List<InputData> inputDataList) {
        Node current;

        if (currentState == null) {
            // Start from the first node
            if (nodes.isEmpty()) return null;
            current = nodes.get(0);
        } else {
            // Resume from the current state
            current = currentState.getCurrentNode();

            // Optionally use providedData to update the state of the system if needed
            // This can include setting values, updating the system state, etc.
        }

        while (current != null) {

            System.out.println("Executing node: " + current.getName());
            ProcessResult result = current.execute(providedData);

            if (!"COMPLETE".equals(result.getStatus())) {
                // Input is required
                currentState = new ExecutionState(current, result);
                return currentState;
            }

            if ("COMPLETE".equals(result.getStatus()) &&
                    result.getInputDataList() != null && !result.getInputDataList().isEmpty()) {
                // Input is required but the node execution is complete.
                current = current.getNextNode();
                result.setStatus("INCOMPLETE");
                currentState = new ExecutionState(current, result);
                return currentState;
            }
            current = current.getNextNode();
        }

        // Clear the state if we reach the end of the execution
        currentState = null;
        return new ExecutionState(null, new ProcessResult("SUCCESSFUL")); // Execution completed
    }
}
