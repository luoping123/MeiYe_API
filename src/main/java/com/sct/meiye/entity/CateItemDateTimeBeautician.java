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
 * @TableName cate_item_date_time_beautician
 */
@TableName(value ="cate_item_date_time_beautician")
@Data
@Accessors(chain = true)
public class CateItemDateTimeBeautician implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 服务分类id
     */
    private Long cateId;

    /**
     * 服务项目id
     */
    private Long serviceItemId;

    /**
     * 日期id
     */
    private Long dateId;

    /**
     * 时间id
     */
    private Long timeId;

    /**
     * 美容师id
     */
    private Long beauticianId;

    /**
     * 剩余可预约数
     */
    private Integer enableOrderNumber;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}