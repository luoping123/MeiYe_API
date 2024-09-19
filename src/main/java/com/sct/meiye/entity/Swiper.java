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
 * @TableName swiper
 */
@TableName(value ="swiper")
@Data
@Accessors(chain = true)
public class Swiper implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 图片路径
     */
    private String imageSrc;

    /**
     * 跳转类型
     */
    private String openType;

    /**
     * 链接地址
     */
    private String navigatorUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}