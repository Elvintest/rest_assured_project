
package ru.atc.fgislk.shared.testcomponents.back.health;


import java.util.List;

public class Health {

    private String status;
    private Components components;
    private List<String> groups = null;

    public String getStatus() {
        return status;
    }

    public Components getComponents() {
        return components;
    }

    public List<String> getGroups() {
        return groups;
    }
}
