// ProcessResult.java
import java.util.List;

public class ProcessResult {
    private String status;
    private List<String> requiredData;

    public ProcessResult(String status, List<String> requiredData) {
        this.status = status;
        this.requiredData = requiredData;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getRequiredData() {
        return requiredData;
    }
}
