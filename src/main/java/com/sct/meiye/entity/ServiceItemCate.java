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
 * @TableName service_item_cate
 */
@TableName(value ="service_item_cate")
@Data
@Accessors(chain = true)
public class ServiceItemCate implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 分类名
     */
    @TableField("cate_name")
    private String name;

    /**
     * 是否启用，0否，1是
     */
    private Boolean disabled;

    /**
     * 是否热卖（小红点）,0否，1是
     */
    private Boolean isHot;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}