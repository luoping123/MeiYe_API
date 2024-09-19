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
 * @TableName limit_kill
 */
@TableName(value ="limit_kill")
@Data
@Accessors(chain = true)
public class LimitKill implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 倒计时，毫秒数
     */
    private Long limitTime;

    /**
     * 是否启用，0否，1是
     */
    private Integer enable;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}