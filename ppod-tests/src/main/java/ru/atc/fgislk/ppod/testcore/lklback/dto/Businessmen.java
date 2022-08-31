package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * Businessmen
 */
@Getter
@Setter
@Builder
public class Businessmen {
    private String lastname = null;
    private String firstname = null;
    private String patronymic = null;
    private String inn = null;
}
