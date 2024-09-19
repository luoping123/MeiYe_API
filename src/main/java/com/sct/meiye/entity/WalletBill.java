package com.sct.meiye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName wallet_bill
 */
@TableName(value ="wallet_bill")
@Data
@Accessors(chain = true)
public class WalletBill implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 充值 or 消费
     */
    private String type;

    /**
     * 记录时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String detailTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}