package com.sct.meiye;

import com.sct.meiye.entity.GridCate;
import com.sct.meiye.entity.Swiper;
import com.sct.meiye.service.GridCateService;
import com.sct.meiye.service.SwiperService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class GridCateTest {

    @Autowired
    private GridCateService gridCateService;

    /**
     * 加入初始数据
     */
    @Test
    public void insertGridCate(){
        List<GridCate> gridCateList=new ArrayList<>();
        for(int i=1;i<=8;i++){
            GridCate gridCate=new GridCate();
            gridCate.setIcon("http://cn.shichengtai.xyz/grid/grid"+ i +".png");
            gridCateList.add(gridCate);
        }
        gridCateService.saveBatch(gridCateList);
    }

}
