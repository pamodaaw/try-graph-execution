package model;

import java.util.ArrayList;
import java.util.List;

public class ProcessResult {
    private String status;
    private List<RequiredData> requiredDataList;

    public ProcessResult(String status) {
        this.status = status;
        this.requiredDataList = new ArrayList<>();
    }

    public ProcessResult(String status, RequiredData data) {
        this.status = status;
        this.requiredDataList = new ArrayList<>();
        this.requiredDataList.add(data);
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RequiredData> getInputDataList() {
        return requiredDataList;
    }

    public void addInputData(RequiredData requiredData) {
        requiredDataList.add(requiredData);
    }

    public void setInputDataList(List<RequiredData> requiredDataList) {
        this.requiredDataList = requiredDataList;
    }
}
