package ru.atc.fgislk.shared.testcomponents.back.dto;

import java.util.List;

public class Filter {
    public String key;
    public List<String> values;
    public String type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
