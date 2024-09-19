package com.sct.meiye;

import com.sct.meiye.entity.Goods;
import com.sct.meiye.entity.GoodsCate;
import com.sct.meiye.entity.ServiceItem;
import com.sct.meiye.entity.ServiceItemCate;
import com.sct.meiye.service.GoodsCateService;
import com.sct.meiye.service.GoodsService;
import com.sct.meiye.service.ServiceItemCateService;
import com.sct.meiye.service.ServiceItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class GoodsTest {

    @Autowired
    private GoodsCateService cateService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 加入初始数据
     */
    @Test
    public void insertGoods(){
        List<GoodsCate> catelist=cateService.list();
        for(GoodsCate cate :catelist){
            List<Goods> goodsList=new ArrayList<>();
            for(int i=1;i<=30;i++){
                Goods goods=new Goods();
                goods.setGoodsCateId(cate.getId());
                goods.setName(cate.getCateName()+"商品 "+i);
                goods.setImageSrc("http://cn.shichengtai.xyz/swiper/swiper3.png");
                goods.setPrice(new BigDecimal(i+"99"));
                goods.setStoreNumber(100);
                goods.setCreateTime(new Date());
                goodsList.add(goods);
            }
            goodsService.saveBatch(goodsList);
        }

    }

}
