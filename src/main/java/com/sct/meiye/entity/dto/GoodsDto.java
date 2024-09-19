package com.sct.meiye.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class GoodsDto implements Serializable {

    private Long id;//主键id

    private Long goodsCateId;//商品分类id

    private String name;//商品名
    
    private String imageSrc;//图片

    private BigDecimal price;//价格

    private BigDecimal olderPrice;//原价

    private Integer intergralValue;//购买获得积分

    private Integer storeNumber;//库存

    private Integer hotNumber;//热度

    private String [] goodsColorList;//商品规格——颜色, 【颜色1，颜色2】

    private String [] goodsSizeList;//商品规格——尺码, 【尺码1，尺码2】

    private String attribute;//属性

    private Integer goodsNum;//商品规格数量

    private Integer isLimitKill;//是否为秒杀，0否，1是，自动转换为false或true

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;//更新时间

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}