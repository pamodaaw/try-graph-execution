package executor;

import model.Element;
import model.RequiredData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmailOTPExecutor implements Executor {

    private final List<Element> elements = new ArrayList<>();

    public String getName() {

        return "executor.EmailOTPExecutor";
    }

    @Override
    public RequiredData process(Map<String, String> input) {

        initialData();
        // Implement the actual task logic here
        if (input != null && !input.isEmpty()) {
            for (Element data : elements) {

                // Check if data.getName is there as a key in the input map.
                if (input.containsKey(data.getName())) {
                    // Check if the value of the key is not null or empty.
                    if (input.get(data.getName()) != null && !input.get(data.getName()).isEmpty()) {
                        // Remove the data from the requiredData list.
                        elements.remove(data);
                    }
                }
            }
        }

        if (!elements.isEmpty()) {
            RequiredData requiredData = new RequiredData("USER_INPUT");
            requiredData.setRequiredData(elements);
            return requiredData;
        }
        return new RequiredData("NONE");
    }

    @Override
    public RequiredData declareRequiredData() {

        return initialData();
    }


    private RequiredData initialData(){

        RequiredData requiredData = new RequiredData("USER_INPUT");
        Element e1 = new Element("attribute", "email", "string", 1);
        requiredData.addRequiredData(e1);
        return requiredData;
    }

    private RequiredData intermediateData(){

        RequiredData requiredData = new RequiredData("USER_INPUT");
        Element e1 = new Element("otp", "email-otp", "string", 1);
        requiredData.addRequiredData(e1);
        return requiredData;
    }
}
