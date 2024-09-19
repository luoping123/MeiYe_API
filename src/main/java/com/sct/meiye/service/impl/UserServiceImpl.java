package com.sct.meiye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sct.meiye.entity.User;
import com.sct.meiye.service.UserService;
import com.sct.meiye.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 15811
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-04-26 14:18:52
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    public boolean updateIntegralById(Integer integral,Long id){
        return userMapper.updateIntegralById(integral,id);
    }
}




