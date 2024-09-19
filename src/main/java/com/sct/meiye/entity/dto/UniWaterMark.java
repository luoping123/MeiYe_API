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
public class UniWaterMark implements Serializable {
    private long timestamp;
    private String appid;
}
