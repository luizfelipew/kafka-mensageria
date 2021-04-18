package br.com.developer.ecommerce;

public class User {

    private final String uuid;

    public User(final String uuid) {
        this.uuid = uuid;
    }

    public String getReportPath() {
        return "target/" + uuid + "-report.txt";
    }

    public String getUuid() {
        return uuid;
    }

}
