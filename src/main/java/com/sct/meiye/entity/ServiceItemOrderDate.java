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
 * @TableName service_item_order_date
 */
@TableName(value ="service_item_order_date")
@Data
@Accessors(chain = true)
public class ServiceItemOrderDate implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    /**
     * 是否启用，0否，1是
     */
    private Boolean enable;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}