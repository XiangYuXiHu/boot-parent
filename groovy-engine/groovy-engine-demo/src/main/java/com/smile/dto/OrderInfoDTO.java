package com.smile.dto;

/**
 * 订单信息
 *
 * @Description
 * @ClassName OrderInfoDTO
 * @Author smile
 * @date 2023.04.05 15:29
 */
public class OrderInfoDTO {

    private Integer orderId;

    private String orderName;

    private String orderNumber;

    private String orderAmount;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Override
    public String toString() {
        return "OrderInfoDTO{" +
                "orderId=" + orderId +
                ", orderName='" + orderName + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderAmount='" + orderAmount + '\'' +
                '}';
    }
}
