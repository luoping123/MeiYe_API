package com.sct.meiye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName goods_details
 */
@TableName(value ="goods_details")
@Data
@Accessors(chain = true)
public class GoodsDetails implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 轮播图图片或视频地址，‘;’分割
     */
    private String swiperUrl;

    /**
     * 轮播图对应的类型（image、video）‘;’分割
     */
    private String swiperType;

    /**
     * 商品价格栏目 类型，0：原价栏目，1：限时抢购栏目
     */
    private Integer priceType;

    /**
     * 运费
     */
    private Integer freight;

    /**
     * 详情富文本
     */
    private String content;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}