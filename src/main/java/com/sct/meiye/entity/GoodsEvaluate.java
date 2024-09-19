package com.sct.meiye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName goods_evaluate
 */
@TableName(value ="goods_evaluate")
@Data
@Accessors(chain = true)
public class GoodsEvaluate implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 被评价的商品id
     */
    private Long goodsId;

    /**
     * 图片、视频地址，‘;’分割
     */
    private String url;

    /**
     * 图片or视频，image;video
     */
    private String urlType;

    /**
     * 评价时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private String evaluateDate;

    /**
     * 好评、中评、差评
     */
    private String evaluateType;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评价星级
     */
    private Integer evaluateStar;

    /**
     * 是否置顶，默认否，1：置顶
     */
    private Boolean isUp;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}