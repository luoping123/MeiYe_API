package com.sct.meiye.common.enums;

/**
 * @author sct
 * @version 1.0
 * @createTime 2022/4/26 14:35
 */
public enum OrderStatus {
    /**
     * 交易成功
     */
    OK("0","SUCCESS"),

    /**
     * 待支付
     */
    NOTPAY("1","NOTPAY"),

    /**
     * 支付中
     */
    PAYING("2","USERPAYING"),

    /**
     * 已退款
     */
    REFUND("3","REFUND"),

    /**
     * 交易关闭
     */
    DEAD("4","CLOSED");

    private final String value;

    private final String status;

    OrderStatus(String value,String status) {
        this.value = value;
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public String getStatus() {
        return status;
    }

}
