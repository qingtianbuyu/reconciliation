package com.payment.reconciliation.engine.model;

import java.io.Serializable;

/**
 * 商户支付数据
 */
public class PayOrgVo implements Serializable {
    private String orderNo;
    private Integer payment;
    private Long eventTime = System.currentTimeMillis();


    public PayOrgVo(String orderNo, Integer payment) {
        this.orderNo = orderNo;
        this.payment = payment;
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

    public PayOrgVo(String orderNo, Integer payment, Long eventTime) {
        this.orderNo = orderNo;
        this.payment = payment;
        this.eventTime = eventTime;
    }

    public PayOrgVo() {
    }

    @Override
    public String toString() {
        return "PayOrgVo{" +
                "orderNo='" + orderNo + '\'' +
                ", payment=" + payment +
                ", eventTime=" + eventTime +
                '}';
    }
}
