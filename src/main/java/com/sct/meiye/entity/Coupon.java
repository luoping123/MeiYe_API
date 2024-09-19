package com.sct.meiye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName coupon
 */
@TableName(value ="coupon")
@Data
public class Coupon implements Serializable {
    /**
     * 优惠券id
     */
    private Long id;

    /**
     * 优惠券面额
     */
    private Integer money;

    /**
     * 兑换所需积分
     */
    private Integer integral;

    /**
     * 剩余数量
     */
    private Integer number;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}