package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthorityDocument {
    private String id = null;
    private String name = null;
    private String issuer = null;
    private String docDate = null;
    private String fileLink = null;
    private String fileName = null;
    private String fileSigLink = null;
    private String fileSigName = null;
}
