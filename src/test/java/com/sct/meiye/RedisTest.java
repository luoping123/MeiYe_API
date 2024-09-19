package com.sct.meiye;

import com.sct.meiye.entity.Video;
import com.sct.meiye.service.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    @Resource
    private  RedisTemplate redisTemplate;

    /**
     * 加入初始数据
     */
    @Test
    public void insertVideo(){

        redisTemplate.opsForValue().set("Test1","987654321",1L, TimeUnit.MINUTES);

    }

}
