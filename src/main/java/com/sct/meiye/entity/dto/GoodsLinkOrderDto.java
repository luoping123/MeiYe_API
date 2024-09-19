package com.sct.meiye.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sct.meiye.entity.Goods;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 
 * @TableName goods_link_order
 */
@TableName(value ="goods_link_order")
@Data
@Accessors(chain = true)
public class GoodsLinkOrderDto implements Serializable {

    private Long id;

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 该商品数量
     */
    private Integer number;

    /**
     * 商品属性
     */
    private String attribute;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}