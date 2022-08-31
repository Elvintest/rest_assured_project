package ru.atc.fgislk.shared.testcomponents.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.atc.fgislk.shared.testcomponents.filestorage.GuidMd5;
import ru.atc.fgislk.shared.testcomponents.filestorage.SimpleFileStorageService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStTest {

    @Test
    public void test1() throws IOException {
        SimpleFileStorageService fileStorage = new SimpleFileStorageService("http://fs.phd.dev.fgislk.at-consulting.ru");
        File file =  File.createTempFile("filestorage",".tmp");
        file.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buf = {1,2,3,4,5,6,7,8,9,10};
        fos.write(buf);
        fos.close();
        GuidMd5 guid = fileStorage.userUpload(file);
        byte[] resBuf = fileStorage.userDownload(guid.getFileGuid());
        Assert.assertEquals(resBuf, buf);
        //-------------------
        File file2 =  File.createTempFile("filestorage",".tmp");
        file2.deleteOnExit();
        fos = new FileOutputStream(file2);
        buf = new byte[]{11,12,13,14,15,16,17,18,19,20};
        fos.write(buf);
        fos.close();
        guid = fileStorage.userUpload(file2);
        resBuf = fileStorage.userDownload(guid.getFileGuid());
        Assert.assertEquals(resBuf, buf);
    }
}
