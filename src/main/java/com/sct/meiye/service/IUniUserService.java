package com.sct.meiye.service;


import com.sct.meiye.entity.Result;
import com.sct.meiye.entity.dto.UniDetail;

/**
 * @author sct
 * @version 1.0
 * @createTime 2022/4/26 14:35
 */
public interface IUniUserService {
    /**
     * @param wxDetail 用户登录的方法
     */
    Result<Object> login(UniDetail wxDetail);

    Result<Object> loginOut(String openId);

    void saveCode(String code);
}
