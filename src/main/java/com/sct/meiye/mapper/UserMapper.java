package com.sct.meiye.mapper;

import com.sct.meiye.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
* @author 15811
* @description 针对表【user】的数据库操作Mapper
* @createDate 2022-04-26 14:18:52
* @Entity com.sct.meiye.entity.User
*/
public interface UserMapper extends BaseMapper<User> {


    @Update("update user set integral= #{integral} " +
            "where id= #{id}")
    boolean updateIntegralById(Integer integral,Long id);

}




