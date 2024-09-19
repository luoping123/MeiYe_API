package com.sct.meiye.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author sct
 * @version 1.0
 * @createTime 2022/4/26 14:35
 */
@Data
@Accessors(chain = true)
public class UniUserDto implements Serializable {
    private String openId;
    private String nickName;
    private Integer gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private UniWaterMark watermark;
}
