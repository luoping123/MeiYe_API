package com.sct.meiye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName beautician_time
 */
@TableName(value ="beautician_time")
@Data
@Accessors(chain = true)
public class BeauticianTime implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 美容师id
     */
    private Long beauticianId;

    /**
     * 可预约时间
     */
    private String beauticianTime;

    /**
     * 当前美容师当前时间可预约数量
     */
    private Integer number;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}