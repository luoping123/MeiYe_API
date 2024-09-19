package com.sct.meiye.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @TableName goods
 */
@Data
@Accessors(chain = true)
public class GoodsVo implements Serializable {

    private Long id;//主键id

    private String name;//商品名
    
    private String imageSrc;//图片

    private BigDecimal price;//价格

    private String attribute;//属性

    private Integer number;//商品规格数量

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}