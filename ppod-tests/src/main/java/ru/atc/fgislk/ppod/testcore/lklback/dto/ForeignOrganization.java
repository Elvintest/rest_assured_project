package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ForeignOrganization {
    public String name = null;
    public String regDate = null;
    public String countryCode = null;
    public String countryName = null;
    public String regNumber = null;
    public String inn = null;
    public String organizationCode = null;
    public Address address = null;
    public String kio = null;
}
