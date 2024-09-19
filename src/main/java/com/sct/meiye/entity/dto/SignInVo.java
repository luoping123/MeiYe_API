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
public class SignInVo implements Serializable {

    private String signNow;

    private String sinNext;

    private Integer sinNumber;

    private String signInKey;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}