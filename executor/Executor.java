package executor;

import model.RequiredData;

import java.util.Map;

// executor.Executor.java
public interface Executor {

    RequiredData declareRequiredData();
    RequiredData process(Map<String, String> input);
}