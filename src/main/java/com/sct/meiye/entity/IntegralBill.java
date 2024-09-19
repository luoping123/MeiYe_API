package com.sct.meiye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName integral_bill
 */
@TableName(value ="integral_bill")
@Data
public class IntegralBill implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
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
     * 积分值
     */
    private Integer value;

    /**
     * 获得 or 兑换
     */
    private String type;

    /**
     * 记录时间
     */
    private Date detailTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}