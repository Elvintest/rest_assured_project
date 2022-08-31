package ru.atc.fgislk.shared.testcomponents.enums;

public enum FileStorageEnum {
    DEV("http://fs.phd.dev.fgislk.at-consulting.ru"),
    UAT("http://fs.phd.uat.fgislk.at-consulting.ru");

    final String fileStorageUrl;

    FileStorageEnum(String url) {
        fileStorageUrl = url;
    }

    public String getFileStorageUrl() {
        return fileStorageUrl;
    }
}
