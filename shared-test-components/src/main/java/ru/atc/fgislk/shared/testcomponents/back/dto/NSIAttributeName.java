package ru.atc.fgislk.shared.testcomponents.back.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NSIAttributeName {
    @JsonProperty("AND")
    public AND AND;

    public AND getAND() {
        return AND;
    }

    public void setAND(AND AND) {
        this.AND = AND;
    }
}
