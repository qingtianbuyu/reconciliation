package com.payment.reconciliation.engine.model;


public class MergeVo {

    private DiffType diffType;
    private PayOrgVo payOrgVo;
    private BankVo bankVo;

    public MergeVo(DiffType diffType, PayOrgVo payOrgVo, BankVo bankVo) {
        this.diffType = diffType;
        this.payOrgVo = payOrgVo;
        this.bankVo = bankVo;
    }

    public MergeVo() {
    }


    public DiffType getDiffType() {
        return diffType;
    }

    public void setDiffType(DiffType diffType) {
        this.diffType = diffType;
    }

    public PayOrgVo getPayOrgVo() {
        return payOrgVo;
    }

    public void setPayOrgVo(PayOrgVo payOrgVo) {
        this.payOrgVo = payOrgVo;
    }

    public BankVo getBankVo() {
        return bankVo;
    }

    public void setBankVo(BankVo bankVo) {
        this.bankVo = bankVo;
    }

    @Override
    public String toString() {
        return "MergeVo{" +
                "diffType='" + diffType + '\'' +
                ", payOrgVo=" + payOrgVo +
                ", bankVo=" + bankVo +
                "}";
    }
}
