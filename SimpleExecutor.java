//// SimpleExecutor.java
//import executor.Executor;
//import model.InputData;
//
//import java.util.List;
//import java.util.ArrayList;
//
//public class SimpleExecutor implements Executor {
//    private String taskDetail;
//    private List<String> requiredData;
//
//    public SimpleExecutor(String taskDetail, List<String> requiredData) {
//        this.taskDetail = taskDetail;
//        if (requiredData == null) {
//            this.requiredData = new ArrayList<>();
//        } else {
//            this.requiredData = new ArrayList<>(requiredData);
//        }
//    }
//
//    @Override
//    public InputData process(List<String> providedData) {
//        // Implement the actual task logic here
//        System.out.println("Processing task: " + taskDetail);
//
//        if (providedData != null && requiredData != null) {
//            // Loop the providedData and remove each from the requiredData.
//            for (String data : providedData) {
//                requiredData.remove(data);
//            }
//        }
//
//        if (requiredData != null && !requiredData.isEmpty()) {
//            return new InputData("USER_INPUT", requiredData);
//        }
//        return new InputData("COMPLETE", null);
//    }
//
//    @Override
//    public List<String> declareRequiredData() {
//        return requiredData;
//    }
//}
