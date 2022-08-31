package ru.atc.fgislk.ppod.testcore.phdfs.dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
//@NoArgsConstructor
//@Accessors(chain = true)
public class PutExtLinksRequest {
    @Singular("oneUriList")
    public List<String> uriList;
    public boolean fn;
}
