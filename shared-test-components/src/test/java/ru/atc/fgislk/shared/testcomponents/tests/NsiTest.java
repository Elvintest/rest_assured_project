package ru.atc.fgislk.shared.testcomponents.tests;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import ru.atc.fgislk.shared.testcomponents.back.CommonBackMethod;

import java.io.IOException;
import java.util.List;


@Slf4j
public class NsiTest{
    String baseURL = "http://pnsi.uat.fgislk.at-consulting.ru";
    Nsi nsi = new Nsi(baseURL);

    @Test
    public void test1() {
        log.info("RecordId = " + nsi.getRecordId("forestComponents", "name", "Пни"));
    }

    @Test
    public void test2(){
        log.info("RecordId = " + nsi.getAnyRecordId("forestComponents"));
    }

    @Test
    public void test3(){
        log.info("Attribute = " + nsi.getAttribute("2849513936825357897", "name"));
        log.info("Attribute = " + nsi.getAttribute("2849513936825357897", "catalogCode"));
    }

    @Test
    public void test4(){
        log.info("Attribute = " + nsi.getAttribute("2849513936825357897", "name, catalogCode"));
        log.info("Attribute = " + nsi.getAttribute("2849513936825357897", "catalogCode"));
    }
}

class Nsi extends CommonBackMethod {
    public Nsi(String apiUrl) {
        super(apiUrl);
    }
}
