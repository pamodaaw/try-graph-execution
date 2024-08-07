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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InputData {

    private String nodeName;
    private Map<String, String> inputData = new HashMap<>();

    public String getNodeName() {

        return nodeName;
    }

    public void setNodeName(String nodeName) {

        this.nodeName = nodeName;
    }

    public Map<String, String> getInputData() {

        return inputData;
    }

    public void setInputData(Map<String, String> inputData) {

        this.inputData = inputData;
    }

    public void addInputData(String key, String value) {

        this.inputData.put(key, value);
    }
}
