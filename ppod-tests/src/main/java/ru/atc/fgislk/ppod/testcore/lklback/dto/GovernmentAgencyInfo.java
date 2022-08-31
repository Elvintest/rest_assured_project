package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GovernmentAgencyInfo {
    private String position = null;
    private String governmentAgencyName = null;
    private String infoDate = null;
    private NotaryDocument notaryDocument = null;
}
