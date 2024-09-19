package com.sct.meiye.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName service_order_order
 */
@TableName(value ="service_order_order")
@Data
@Accessors(chain = true)
public class ServiceOrderOrder implements Serializable {
    /**
     * 主键订单号
     */
    private String orderId;

    /**
     * 二维码号码
     */
    private String qrcodeNumber;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建订单时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    /**
     * 服务项目id
     */
    private Long serviceItemId;

    /**
     * 日期id
     */
    private Long dateId;

    /**
     * 时间id
     */
    private Long timeId;

    /**
     * 美容师id
     */
    private Long beauticianId;

    /**
     * 客户id
     */
    private Long uid;

    /**
     * 微信用户id
     */
    private String openId;

    /**
     * 到期时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endDate;

    /**
     * 服务方式
     */
    private String serviceType;

    /**
     * 预约时间(只用于显示)
     */
    private String orderTime;

    /**
     * 预约时间(存储)
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String serviceOrderDatetime;

    /**
     * 订单总价
     */
    private BigDecimal sumPrice;

    /**
     * 实付款
     */
    private BigDecimal realPay;

    /**
     * 付款方式
     */
    private String payType;

    /**
     * 付款时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String payTime;

    /**
     * 订单状态
     */
    private String orderStatus;

    @TableLogic//逻辑删除
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}