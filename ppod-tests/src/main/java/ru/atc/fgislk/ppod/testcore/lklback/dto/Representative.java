package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class Representative {
    private String lastname = null;
    private String firstname = null;
    private String patronymic = null;
    private IdentityDocument identityDocument = null;
    private String snils = null;
    private String email = null;
    private Address address = null;
    private AuthorityDocument authorityDocument = null;
}
