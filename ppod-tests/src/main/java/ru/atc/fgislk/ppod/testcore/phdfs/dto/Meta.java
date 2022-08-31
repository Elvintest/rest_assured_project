package ru.atc.fgislk.ppod.testcore.phdfs.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Meta {
    private String originalName;
    private int size;
    private String md5;
    private boolean expired;
    private long removeBefore;
    private Attributes attributes;
    private String contentType;
    private String source;
}
