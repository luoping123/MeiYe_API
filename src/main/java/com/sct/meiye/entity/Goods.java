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
 * @TableName goods
 */
@TableName(value ="goods")
@Data
@Accessors(chain = true)
public class Goods implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 商品分类id
     */
    private Long goodsCateId;

    /**
     * 商品名
     */
    private String name;

    /**
     * 图片
     */
    private String imageSrc;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 原价
     */
    private BigDecimal olderPrice;

    /**
     * 购买获得积分
     */
    private Integer intergralValue;

    /**
     * 库存
     */
    private Integer storeNumber;

    /**
     * 热度
     */
    private Integer hotNumber;

    /**
     * 商品规格——颜色, 颜色1;颜色2;
     */
    private String goodsColor;

    /**
     * 商品规格——尺码, 尺码1;尺码2;
     */
    private String goodsSize;

    /**
     * 商品规格——数量
     */
    private Integer goodsNum;

    /**
     * 是否为秒杀，0否，1是，自动转换为false或true
     */
    private Integer isLimitKill;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    //是否为积分兑换商品，0否，1是，自动转换为false或true
    private Integer isIntegralExchange;

    //兑换所需积分
    private Integer integral;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}