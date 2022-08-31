package ru.atc.fgislk.ppod.testcore.phdfs.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompressRequest {
    public String zipFileName;
    public String zipFileLink;
    public FilesToCompress filesToCompress;
}
