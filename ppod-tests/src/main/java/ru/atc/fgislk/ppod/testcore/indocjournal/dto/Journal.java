package ru.atc.fgislk.ppod.testcore.indocjournal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Журнал учета входящих документов
 */
@Getter
@Setter
@Builder
public class Journal {
    private Long id = null;
    private String regNumber = null;
    private String status = null;
    private Integer year = null;
    private String createdAt = null;
    private String closedAt = null;
}
