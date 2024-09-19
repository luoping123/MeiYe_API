package com.sct.meiye;

import com.sct.meiye.entity.Goods;
import com.sct.meiye.entity.GoodsCate;
import com.sct.meiye.entity.GoodsEvaluate;
import com.sct.meiye.entity.User;
import com.sct.meiye.service.GoodsCateService;
import com.sct.meiye.service.GoodsEvaluateService;
import com.sct.meiye.service.GoodsService;
import com.sct.meiye.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class GoodsEvaluateTest {

    @Autowired
    private GoodsEvaluateService goodsEvaluateService;
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 加入初始数据
     */
    @Test
    public void insertGoodsEvaluate(){
        String urlStr="";
        String urlTypeStr="";
        for(int i=1;i<=9;i++){//评价图片
            urlStr+="http://cn.shichengtai.xyz/swiper/swiper3.png;";
            urlTypeStr+="image;";
        }

        List<User> userList=userService.list();
        for(User user :userList){
            List<Goods> goodsList=goodsService.list();
            for(Goods goods : goodsList){
                List<GoodsEvaluate> goodsEvaluateList=new ArrayList<>();
                for(int i=1;i<=5;i++){
                    GoodsEvaluate evaluate=new GoodsEvaluate();
                    evaluate.setUserId(user.getId());
                    evaluate.setGoodsId(goods.getId());
                    evaluate.setUrl(urlStr);
                    evaluate.setUrlType(urlTypeStr);
                    evaluate.setEvaluateDate(new Date().toString());
                    String evaluateType="好评";
                    Integer star=5;
                    if(i%3==0){
                        evaluateType="中评";
                        star=3;
                    }else if(i%5==0){
                        evaluateType="差评";
                        star=1;
                    }
                    evaluate.setEvaluateStar(star);
                    evaluate.setEvaluateType(evaluateType);
                    evaluate.setContent("非常好用，种草啦~");
                    if(i==21){
                        evaluate.setIsUp(true);
                    }

                    goodsEvaluateList.add(evaluate);
                }
                goodsEvaluateService.saveBatch(goodsEvaluateList);
            }

        }

    }

}
