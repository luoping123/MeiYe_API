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
public class UniOpenData implements Serializable {
    private String session_key;
    private String openid;
    private String errcode;
    private String errmsg;
    private String rid;
}
