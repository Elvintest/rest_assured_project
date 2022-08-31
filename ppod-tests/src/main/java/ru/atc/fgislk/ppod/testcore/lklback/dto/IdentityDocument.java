package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * IdentityDocument
 */
@Getter
@Setter
@Builder
public class IdentityDocument {
    public String id = null;
    public Type type = null;
    public String name = null;
    public String series = null;
    public String number = null;
    public String issueName = null;
    public String issueCode = null;
    public String issueDate = null;
}
