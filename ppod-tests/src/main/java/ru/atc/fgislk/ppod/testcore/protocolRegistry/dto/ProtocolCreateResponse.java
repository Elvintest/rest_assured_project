package ru.atc.fgislk.ppod.testcore.protocolRegistry.dto;

import lombok.*;


/**
 * Запрос на создание протокола
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolCreateResponse {
    private int id;
    private String processInstance;
    private String sourceSubsystem;
    private String kind;
    private String status;
    private String createdAt;
    private String decision;
    private String closedAt;
    private String reportXmlFile;
    private String reportXmlSignFile;
    private String reportPrintFormFile;


}

