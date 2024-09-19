package com.sct.meiye.entity.dto;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author sct
 * @version 1.0
 * @createTime 2022/4/26 14:35
 */
@Data
@Accessors(chain = true)
public class UniDetail implements Serializable {
    private String id;
    @NotNull
    private String code;//用户登录凭证  可通过 wx.login() 获取
    @NotNull
    private String encryptedData;//完整用户信息密文   可通过 wx.getUserProfile获取
    @NotNull
    private String iv;//加密算法的初始向量  可通过 wx.getUserProfile获取
    @NotNull
    private String rawData;//用户信息原始数据字符串  可通过 wx.getUserProfile获取
    @NotNull
    private String signature;//使用 sha1得到字符串  可通过 wx.getUserProfile获取
}
