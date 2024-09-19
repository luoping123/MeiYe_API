package com.sct.meiye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName service_item_order_time
 */
@TableName(value ="service_item_order_time")
@Data
@Accessors(chain = true)
public class ServiceItemOrderTime implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 时间
     */
    @JsonFormat(pattern = "HH:mm:ss")
    private Date time;

    /**
     * 是否启用,0否，1是
     */
    private Boolean enable;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}