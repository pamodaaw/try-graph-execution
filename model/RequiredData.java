package model;

import model.Element;

import java.util.ArrayList;
import java.util.List;

public class RequiredData {
    private String inputType;
    private String nodeName;
    private List<Element> requiredData;

    public RequiredData(String inputType) {
        this.inputType = inputType;
        this.requiredData = new ArrayList<>();
    }

    public String getNodeName() {

        return nodeName;
    }

    public void setNodeName(String nodeName) {

        this.nodeName = nodeName;
    }

    // Getters and Setters
    public String getInputType() {

        return inputType;
    }

    public void setInputType(String inputType) {

        this.inputType = inputType;
    }

    public List<Element> getRequiredData() {

        return requiredData;
    }

    public void setRequiredData(List<Element> requiredData) {

        this.requiredData = requiredData;
    }

    // Add model.Element to requiredData list.
    public void addRequiredData(Element element) {

        requiredData.add(element);
    }
}

