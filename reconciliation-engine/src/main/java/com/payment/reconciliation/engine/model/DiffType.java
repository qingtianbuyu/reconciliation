package com.payment.reconciliation.engine.model;

public enum DiffType {
    /**
     * 对平
     */
    MATCH,
    /**
     * 长款
     */
    SRC_MORE,
    /**
     * 短款
     */
    TARGET_MORE,
    /**
     * 错账
     */
    MIS;

}
