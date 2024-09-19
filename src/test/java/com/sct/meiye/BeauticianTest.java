package com.sct.meiye;

import com.sct.meiye.entity.Beautician;
import com.sct.meiye.service.BeauticianService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BeauticianTest {

    @Autowired
    private BeauticianService beauticianService;


//    @Test
//    public void saveBeautician(){
//        Beautician beautician=new Beautician();
//        beautician.setName("测试")
//                .setSex("女")
//                .setAge(18)
//                .setImg("w484d8a48w")
//                .setTel("187987849789")
//                .setAddress("北京市朝阳区")
//                .setStarSum(4.9)
//                .setEvaluate("评价哈");
//        beauticianService.save(beautician);
//
//
//    }

}
