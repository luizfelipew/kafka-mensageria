package br.com.developer.ecommerce;

import java.util.UUID;

public class CorrelationId {

    private final String id;

    public CorrelationId() {
        id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CorrelationId{");
        sb.append("id='").append(id).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
