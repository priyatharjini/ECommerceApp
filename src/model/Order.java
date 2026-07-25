package model;

import java.util.Date;

public class Order {
    private int orderId;
    private int userId;
    private double totalAmount;
    private Date orderDate;

    public Order(int orderId, int userId, double totalAmount, Date orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
    }
}
