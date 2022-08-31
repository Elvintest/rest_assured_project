package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class NotaryDocument {
    private String name = null;
    private String series = null;
    private String number = null;
    private String issueName = null;
    private String issueDate = null;
}
