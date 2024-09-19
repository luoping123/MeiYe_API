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
 * @TableName beautician
 */
@TableName(value ="beautician")
@Data
@Accessors(chain = true)
public class Beautician implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 美容师姓名
     */
    private String name;

    /**
     * 美容师性别
     */
    private String sex;

    /**
     * 美容师年龄
     */
    private Integer age;

    /**
     * 美容师家庭地址
     */
    private String address;

    /**
     * 美容师电话
     */
    private String tel;

    /**
     * 美容师照片
     */
    private String img;

    /**
     * 评分总星级
     */
    private Double starSum;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}