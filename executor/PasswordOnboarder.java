package executor;

import executor.Executor;
import model.Element;
import model.RequiredData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PasswordOnboarder implements Executor {

    private List<Element> elements = new ArrayList<>();

    public String getName() {

        return "executor.PasswordOnboarder";
    }

    @Override
    public RequiredData process(Map<String, String> input) {

        declareRequiredData();
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

        RequiredData requiredData = new RequiredData("USER_INPUT");
        Element e1 = new Element("credential", "password", "string", 1);
        requiredData.addRequiredData(e1);
        return requiredData;
    }
}
