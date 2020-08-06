package br.com.developer.ecommerce;

import java.math.BigDecimal;

public class Order {

    private final String userId, orderId;
    private final BigDecimal amount;
    private final String email;

    public Order(final String userId, final String orderId, final BigDecimal amount, final String email) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.email = email;
    }

}
