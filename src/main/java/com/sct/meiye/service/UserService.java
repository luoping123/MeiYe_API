package com.sct.meiye.service;

import com.sct.meiye.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 15811
* @description 针对表【user】的数据库操作Service
* @createDate 2022-04-26 14:18:52
*/
public interface UserService extends IService<User> {

    boolean updateIntegralById(Integer integral,Long id);
}
