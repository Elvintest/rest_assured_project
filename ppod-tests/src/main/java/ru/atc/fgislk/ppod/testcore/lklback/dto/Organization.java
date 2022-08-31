package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Organization
 */
@Getter
@Setter
@Builder
public class Organization {
    private String name = null;
    private String inn = null;
    private String regDate = null;
    private String ogrn = null;
    private String kpp = null;
    private Address address = null;
}
