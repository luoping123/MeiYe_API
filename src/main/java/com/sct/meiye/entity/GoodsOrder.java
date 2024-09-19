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
 * @TableName goods_order
 */
@TableName(value ="goods_order")
@Data
@Accessors(chain = true)
public class GoodsOrder implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 订单编号
     */
    private String goodsOrderId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 微信登录用户为openId
     */
    private String openId;

    /**
     * 商品id;商品id;
     */
    private String goodsIdList;

    /**
     * 留言
     */
    private String remark;

    /**
     * 订单总积分
     */
    private Integer intergralSum;

    /**
     * 订单总金额
     */
    private BigDecimal priceSum;

    /**
     * 实际金额
     */
    private BigDecimal realPrice;

    /**
     * 优惠券减少金额
     */
    private String useCoupon;

    /**
     * 订单状态
     */
    private String orderStatus;

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
     * 创建订单时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    /**
     * 付款截止时间，到这个时间后未付款，订单失效，用于倒计时  【该属性废弃，改用redis倒计时】
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String payDeadline;

    @TableLogic//逻辑删除
    private Integer isDeleted;

    /**
     * 是否超时未支付
     */
    private Integer isOutTime;

    private String receiverName;//收件人姓名

    private String receiverAddress;//收件地址

    private String receiverPhone;//收件人电话号码

    private String postalNumber;//快递单号

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}