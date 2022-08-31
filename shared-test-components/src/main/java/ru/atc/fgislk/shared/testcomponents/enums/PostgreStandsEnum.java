package ru.atc.fgislk.shared.testcomponents.enums;

public enum PostgreStandsEnum {
    DEV_POPD("jdbc:postgresql://10.125.4.117:5432/gisdb","gis","gis"),
    UAT_POPD("jdbc:postgresql://10.125.7.19:5432/gisdb","gis","gis");
    final String url;
    final String user;
    final String password;

    PostgreStandsEnum(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
