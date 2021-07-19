package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private long id;
    private String userName;
    private String password;
    private String orderNumber;
    private long amount;
    private int currency;
    private String returnUrl;
    private String failUrl;
    private String status;
    private Date deletedAt;
}
