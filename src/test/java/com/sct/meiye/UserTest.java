package com.sct.meiye;

import com.sct.meiye.entity.Goods;
import com.sct.meiye.entity.GoodsCate;
import com.sct.meiye.entity.User;
import com.sct.meiye.service.GoodsCateService;
import com.sct.meiye.service.GoodsService;
import com.sct.meiye.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;


    /**
     * 加入初始数据
     */
    @Test
    public void insertUser(){
        List<User> userList=new ArrayList<>();
        for(int i=1;i<=10;i++){
            User user=new User();
            user.setName("用户"+i)
                    .setAddress("地址"+i)
                    .setAge(i+10)
                    .setUsername("user"+i)
                    .setPassword("123456")
                    .setPhone("178667158"+i)
                    .setGender(i%2)
                    .setVipId(10045454L+i)
                    .setBalance(new BigDecimal("299.65"))
                    .setIntegral(300)
                    .setCreateTime(new Timestamp(System.currentTimeMillis()))
                    .setLastLoginTime(new Timestamp(System.currentTimeMillis()));
            userList.add(user);
        }
        userService.saveBatch(userList);

    }

}
