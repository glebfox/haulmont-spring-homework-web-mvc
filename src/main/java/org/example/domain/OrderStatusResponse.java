package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusResponse {
    private String orderNumber;
    private long amount;
    private int currency;
    private String status;

    public static OrderStatusResponse fromOrder(Order order) {
        return new OrderStatusResponse(
                order.getOrderNumber(), order.getAmount(), order.getCurrency(), order.getStatus());
    }
}
