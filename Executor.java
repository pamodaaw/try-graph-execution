import java.util.List;

// Executor.java
public interface Executor {

    ProcessResult process(List<String> providedData);
    List<String> declareRequiredData();

}