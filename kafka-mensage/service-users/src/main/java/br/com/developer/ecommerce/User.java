package br.com.developer.ecommerce;

public class User {

    private final String uuid;

    public User(final String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

}
