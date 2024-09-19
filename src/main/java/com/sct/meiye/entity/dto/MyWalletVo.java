package com.sct.meiye.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 
 * @TableName goods
 */
@Data
@Accessors(chain = true)
public class MyWalletVo implements Serializable {

    private String balance;

    private Long userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}