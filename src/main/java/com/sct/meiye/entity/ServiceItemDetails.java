package com.sct.meiye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName service_item_details
 */
@TableName(value ="service_item_details")
@Data
public class ServiceItemDetails implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 服务项目id
     */
    private Long serviceId;

    /**
     * 轮播图图片或视频地址，‘;’分割
     */
    private String swiperUrl;

    /**
     * 轮播图对应的类型（image、video）‘;’分割
     */
    private String swiperType;

    /**
     * 详情富文本
     */
    private String content;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}