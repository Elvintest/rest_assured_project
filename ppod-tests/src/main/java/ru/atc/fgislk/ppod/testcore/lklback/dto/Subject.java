package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Subject
 */
@Getter
@Setter
@Builder
public class Subject {
    private String id = null;
    private String type = null;
    private Person person = null;
    private Businessmen businessmen = null;
    private Organization organization = null;
    private ForeignOrganization foreignOrganization = null;
    private EmployeeInfo employeeInfo = null;
    private String email = null;
    private String phone = null;
    private Representative representative = null;
    private GovernmentAgencyInfo governmentAgencyInfo = null;
    private boolean confirmProcessing;
    private boolean confirmAccuracy;
}
