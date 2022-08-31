package ru.atc.fgislk.shared.testcomponents.filestorage;

import ru.atc.fgislk.shared.testcomponents.Util;

public class GuidMd5 {
    private String fileGuid;
    private String fileDigest;

    public GuidMd5(String guid, String md5) {
        this.fileGuid = guid;
        this.fileDigest = md5;
    }

    public GuidMd5() {

    }

    public String getFileGuid() {
        return fileGuid;
    }

    public GuidMd5 fileGuid(String guid) {
        this.fileGuid = guid;
        return this;
    }

    public String getFileDigest() {
        return fileDigest;
    }

    public GuidMd5 fileDigest(String md5) {
        this.fileDigest = md5;
        return this;
    }

    @Override
    public String toString() {
        return Util.GSON.toJson(this);
    }
}
