package com.sct.meiye;

import com.sct.meiye.entity.Swiper;
import com.sct.meiye.service.SwiperService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SwiperTest {

    @Autowired
    private SwiperService swiperService;

    @Test
    public void insertSwiper(){
        List<Swiper> swiperList=new ArrayList<>();
        for(int i=1;i<=4;i++){
            Swiper swiper=new Swiper();
            swiper.setImageSrc("http://cn.shichengtai.xyz/swiper/swiper"+ i +".png");
            swiperList.add(swiper);
        }
        swiperService.saveBatch(swiperList);
    }

}
