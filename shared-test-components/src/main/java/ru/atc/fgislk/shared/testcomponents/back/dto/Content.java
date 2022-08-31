package ru.atc.fgislk.shared.testcomponents.back.dto;

import lombok.Getter;

@Getter
public class Content {
    public String recordId;
    public String catalogCode;
    public String statusCode;
    public String actualStartDt;
    public String createDttm;
    public String modifyDttm;
    public AttributeSet attributeSet;
}
