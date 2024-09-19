package com.sct.meiye.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author sct
 * @version 1.0
 * @createTime 2022/4/26 14:35
 */
@Data
@Accessors(chain = true)
public class UniUserVo implements Serializable {
    private Long userId;
    private String openId;
    private String token;
    private String nickName;
    private Integer gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private Long vipId;//会员卡id
    private BigDecimal balance;//余额
    private Integer integral;//积分
}
