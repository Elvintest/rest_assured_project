package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

@Getter
@Setter
@Builder
public class ExtractionGlrInfo {
    @Singular("oneRequestesInfo")
    private List<RequestesInfo> requestesInfo = null;
}
