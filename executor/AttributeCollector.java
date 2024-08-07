package executor;

import model.Element;
import model.RequiredData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttributeCollector implements Executor {

    private String name;
    private final List<Element> requiredData = new ArrayList<>();

    public AttributeCollector(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void addRequiredData(Element element) {

        requiredData.add(element);
    }

    @Override
    public RequiredData process(Map<String, String> input) {
        // Implement the actual task logic here
        if (input != null && !input.isEmpty()) {
            for (Element data : requiredData) {

                // Check if data.getName is there as a key in the input map.
                if (input.containsKey(data.getName())) {
                    // Check if the value of the key is not null or empty.
                    if (input.get(data.getName()) != null && !input.get(data.getName()).isEmpty()) {
                        // Remove the data from the requiredData list.
                        requiredData.remove(data);
                    }
                }
            }
        }

        if (requiredData != null && !requiredData.isEmpty()) {
            RequiredData inputs = new RequiredData("USER_INPUT");
            inputs.setRequiredData(requiredData);
            return inputs;
        }
        return new RequiredData("NONE");
    }

    @Override
    public RequiredData declareRequiredData() {

    RequiredData requiredData = new RequiredData("USER_INPUT");
    requiredData.setRequiredData(this.requiredData);
    return requiredData;
    }
}
