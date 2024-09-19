package com.sct.meiye;

import com.sct.meiye.entity.Goods;
import com.sct.meiye.entity.GoodsCate;
import com.sct.meiye.entity.GoodsDetails;
import com.sct.meiye.service.GoodsCateService;
import com.sct.meiye.service.GoodsDetailsService;
import com.sct.meiye.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class GoodsDetailsTest {

    @Autowired
    private GoodsDetailsService goodsDetailsService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 加入初始数据
     */
    @Test
    public void insertGoodsDetails(){
        String urlStr="";
        String urlTypeStr="";
        for(int i=1;i<=4;i++){//商品详情轮播图
            urlStr+="http://cn.shichengtai.xyz/swiper/swiper3.png;";
            urlTypeStr+="image;";
        }
        List<Goods> goodsList=goodsService.list();
        List<GoodsDetails> goodsDetailsList=new ArrayList<>();
        for(Goods goods :goodsList){
            GoodsDetails goodsDetails=new GoodsDetails();
            goodsDetails.setGoodsId(goods.getId());
            goodsDetails.setSwiperType(urlTypeStr);
            goodsDetails.setSwiperUrl(urlStr);
            goodsDetails.setPriceType(1);//限时抢购
            goodsDetails.setFreight(0);//运费
            //商品详情富文本
            goodsDetails.setContent("<div class=\"m-img\"><img src=\"https://zhedplus.oss-cn-hangzhou.aliyuncs.com/content_img/20191118/1fb5ff162f25fd4c7383bd998ff2fde9.jpg\"><div class=\"tools\" hidden><i class=\"fa fa-arrow-up move-up\"></i><i class=\"fa fa-arrow-down move-down\"></i><em class=\"move-remove\" hidden ><i class=\"fa fa-times\" aria-hidden=\"true\"></i> 移除</em><div class=\"cover\"></div></div></div>");

            goodsDetailsList.add(goodsDetails);
        }
        goodsDetailsService.saveBatch(goodsDetailsList);

    }

}
