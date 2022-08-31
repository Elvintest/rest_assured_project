package ru.atc.fgislk.ppod.testcore.lklback.enums;

public enum TypeEnumOut {
    APPLICATION_XML("application_xml"),
    APPLICATION_XML_SIG("application_xml_sig"),
    REQUEST_XML("request_xml"),
    UNKNOWN("unknown");

    private String name;

    TypeEnumOut(String value) {
        this.name = value;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }
    public static TypeEnumOut fromValue(String input) {
        for (TypeEnumOut b : TypeEnumOut.values()) {
            if (b.name.equals(input)) {
                return b;
            }
        }
        return null;
    }
}
