package ru.atc.fgislk.shared.testcomponents.back.dto;

import lombok.Getter;

import java.util.ArrayList;
@Getter
public class NSIrecordId {
    public ArrayList<Content> content;
    public Pageable pageable;
    public boolean last;
    public int totalPages;
    public int totalElements;
    public Sort sort;
    public boolean first;
    public int number;
    public int numberOfElements;
    public int size;
    public boolean empty;

}
