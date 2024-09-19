package com.sct.meiye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName goods_attribute
 */
@TableName(value ="goods_attribute")
@Data
public class GoodsAttribute implements Serializable {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 属性名
     */
    private String attributeName;

    /**
     * 属性值
     */
    private String attributeValue;

    /**
     * 该属性值对应的库存
     */
    private Integer storeNumber;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}