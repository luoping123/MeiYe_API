package com.sct.meiye;

import com.sct.meiye.entity.GridCate;
import com.sct.meiye.entity.ServiceItem;
import com.sct.meiye.entity.ServiceItemCate;
import com.sct.meiye.service.GridCateService;
import com.sct.meiye.service.ServiceItemCateService;
import com.sct.meiye.service.ServiceItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ServiceItemTest {

    @Autowired
    private ServiceItemService serviceItemService;

    @Autowired
    private ServiceItemCateService serviceItemCateService;

    /**
     * 加入初始数据
     */
    @Test
    public void insertService(){
        List<ServiceItemCate> catelist=serviceItemCateService.list();
        for(ServiceItemCate cate :catelist){
            List<ServiceItem> serviceItemList=new ArrayList<>();
            for(int i=1;i<=30;i++){
                ServiceItem serviceItem=new ServiceItem();
                serviceItem.setCateId(cate.getId());
                serviceItem.setName(cate.getName()+"服务项目"+i);
                serviceItem.setSubName(cate.getName()+"服务项目二级名"+i);
                serviceItem.setImageSrc("http://cn.shichengtai.xyz/swiper/swiper3.png");
                serviceItem.setOlderPrice(new BigDecimal("599"));
                serviceItem.setNowPrice(new BigDecimal("299"));
                serviceItemList.add(serviceItem);
            }
            serviceItemService.saveBatch(serviceItemList);
        }

    }

}
