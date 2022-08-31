package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

@Getter
@Setter
@Builder
public class Application {
    public String applicationKind;
    public DeclarationInfo declarationInfo;
    public Region region;
    @Singular
    public List<Subject> subjects;
}
