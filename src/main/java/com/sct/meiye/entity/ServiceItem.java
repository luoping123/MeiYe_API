package com.sct.meiye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName service_item
 */
@TableName(value ="service_item")
@Data
@Accessors(chain = true)
public class ServiceItem implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 服务名
     */
    private String name;

    /**
     * 二级服务名
     */
    private String subName;

    /**
     * 
     */
    private String imageSrc;

    /**
     * 原价
     */
    private BigDecimal olderPrice;

    /**
     * 现价
     */
    private BigDecimal nowPrice;

    /**
     * 类别id
     */
    private Long cateId;

    /**
     * 是否为秒杀，0否，1是
     */
    private Boolean isLimitKill;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}