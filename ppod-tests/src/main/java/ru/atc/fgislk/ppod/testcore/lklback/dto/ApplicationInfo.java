package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

/**
 * ApplicationInfo
 */
@Getter
@Setter
@Builder
public class ApplicationInfo {
    private String id = null;
    private String applicationKind = null;
    private String status = null;
    private String statusName = null;
    private DictionaryItem region = null;
    @Singular
    private List<Subject> subjects = null;
    private DeclarationInfo declarationInfo = null;
    @Singular
    private List<DocumentInfo> documents = null;
}
