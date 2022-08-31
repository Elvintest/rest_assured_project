package ru.atc.fgislk.shared.testcomponents.camunda;

/**
 * прямо очень обрезанные сведения о процессе
 */
public class ProcessDefinition {
    private String id;
    private String key;
    private String name;

    public String getId() {
        return id;
    }

    public String getKey() {
        return key.trim();
    }

    public String getName() {
        return name.trim();
    }
}
