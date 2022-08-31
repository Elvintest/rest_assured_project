package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestesInfo {
    private String requestKind = null;
    private String number = null;
    private boolean sectioni;
    private boolean sectionii;
    private boolean sectioniii;
    private boolean sectioniv;
    private boolean sectionv;
    private boolean sectionvi;
    private boolean sectionvii;
    private boolean sectionviii;
    private boolean sectionix;
    private boolean sectionAll;
    private String description = null;
}
