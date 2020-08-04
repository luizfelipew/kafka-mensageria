package br.com.developer.ecommerce;

import java.math.BigDecimal;

public class Order {

    private final String userId, orderId;
    private final BigDecimal amount;

    public Order(final String userId, final String orderId, final BigDecimal amount) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
    }

    public String getEmail() {
        return "email";
    }

}
