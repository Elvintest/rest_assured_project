package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Person
 */
@Getter
@Setter
@Builder
public class Person {
    private String lastname = null;
    private String firstname = null;
    private String patronymic = null;
    private IdentityDocument identityDocument = null;
    private String inn = null;
    private String snils = null;
    private Address address = null;
}
