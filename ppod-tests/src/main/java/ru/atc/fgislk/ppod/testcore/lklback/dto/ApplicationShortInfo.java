package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * ApplicationShortInfo
 */
@Getter
@Setter
@Builder
public class ApplicationShortInfo {
    private String id = null;
    private String number = null;
    private String userId = null;
    private String userName = null;
    private String kind = null;
    private String kindName = null;
    private String status = null;
    private String statusName = null;
    private String createdDate = null;
    private String modifyDate = null;
    private String sentDate = null;
}
