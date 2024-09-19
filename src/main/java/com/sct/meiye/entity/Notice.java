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
 * @TableName notice
 */
@TableName(value ="notice")
@Data
@Accessors(chain = true)
public class Notice implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 滚动的文字
     */
    private String content;

    /**
     * 是否显示,0否，1是
     */
    private Integer isShow;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}