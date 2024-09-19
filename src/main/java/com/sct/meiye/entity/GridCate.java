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
 * @TableName grid_cate
 */
@TableName(value ="grid_cate")
@Data
@Accessors(chain = true)
public class GridCate implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 图标地址
     */
    private String icon;

    /**
     * 宫格名字
     */
    private String name;

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