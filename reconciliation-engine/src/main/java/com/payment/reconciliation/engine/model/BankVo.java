package com.payment.reconciliation.engine.model;

import java.io.Serializable;

public class BankVo implements Serializable {
    private String orderNo;
    private Integer payment;
    private Long eventTime = System.currentTimeMillis();

    public BankVo(String orderNo, Integer payment) {
        this.orderNo = orderNo;
        this.payment = payment;
    }

    public BankVo() {
    }

    public BankVo(String orderNo, Integer payment, Long eventTime) {
        this.orderNo = orderNo;
        this.payment = payment;
        this.eventTime = eventTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public void setEventTime(Long eventTime) {
        this.eventTime = eventTime;
    }

    @Override
    public String toString() {
        return "BankVo{" +
                "orderNo='" + orderNo + '\'' +
                ", payment=" + payment +
                ", eventTime=" + eventTime +
                '}';
    }
}
