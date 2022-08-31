package ru.atc.fgislk.shared.testcomponents.tests;

import org.testng.annotations.Test;
import org.testng.internal.collections.Pair;
import ru.atc.fgislk.shared.testcomponents.back.BaseRestService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class BaseRestServiceTest {

//    @Test
    public void test1() {
//        Service srv = new Service("http://192.168.24.6:30095");
//        Service srv = new Service("http://reporting.popd.dev.fgislk.at-consulting.ru");
        Service srv = new Service("http://load.popd.dev.fgislk.at-consulting.ru");
//        Pair<Integer, byte[]> res = srv.getBytesMethod();
        Map<String, String> params = new HashMap<>();
        params.put("schema", "test");
        params.put("layer", "maxim");
        Pair<Integer, String> res = srv.loadFromShp("/load/from_shp", params, "meltipart",
                new File("c:\\Work\\Les\\Files\\test.naz_one_part_3395.cpg"),
                new File("c:\\Work\\Les\\Files\\test.naz_one_part_3395.dbf"),
                new File("c:\\Work\\Les\\Files\\test.naz_one_part_3395.prj"),
                new File("c:\\Work\\Les\\Files\\test.naz_one_part_3395.shp"),
                new File("c:\\Work\\Les\\Files\\test.naz_one_part_3395.shx")
        );
    }
}


class Service extends BaseRestService {
    public Service(String apiUrl) {
        super(apiUrl);
    }

    public Pair<Integer, byte[]> getBytesMethod() {
        return getBytes("/export/shp?layer=test.naz_one_part", "тестовый запрос");
    }

    public Pair<Integer, byte[]> postByteMethod() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("qwe", "1");
        params.put("asd", "2");

        return postBytes("/report/jpeg/export", "{\"red\": 255,\"green\": 0,\"blue\": 0}", "Пример", params);
    }

    public Pair<Integer, String> loadFromShp(String path, Map<String, String> params, String allureName, File... files) {
        return postMultipart(path,params, allureName, files);
    }

}