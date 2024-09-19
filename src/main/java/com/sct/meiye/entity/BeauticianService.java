package com.sct.meiye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName beautician_service
 */
@TableName(value ="beautician_service")
@Data
public class BeauticianService implements Serializable {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 服务项目id
     */
    private Long serviceId;

    /**
     * 美容师id
     */
    private Long beauticianId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}