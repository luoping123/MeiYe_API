package com.sct.meiye.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sct.meiye.entity.Goods;
import com.sct.meiye.entity.LimitKill;
import com.sct.meiye.entity.Result;
import com.sct.meiye.entity.ServiceItem;
import com.sct.meiye.service.GoodsService;
import com.sct.meiye.service.LimitKillService;
import com.sct.meiye.service.ServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/sct/api/meiye/limit_kill")
public class LimitKillController {

    @Autowired
    private LimitKillService limitKillService;

    @Autowired
    private ServiceItemService serviceItemService;

    @Autowired
    private GoodsService goodsService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;//redis缓存操作模板工具类

    @GetMapping("/getLimitKill")
    public Result<Object> getLimitKillList(){
        Map<String,Object> map=new HashMap<>();
        List list=serviceItemService.list(new QueryWrapper<ServiceItem>().lambda().eq(ServiceItem::getIsLimitKill,1));
        List goodsList=goodsService.list(new QueryWrapper<Goods>().lambda().eq(Goods::getIsLimitKill,1));
        list.addAll(goodsList);
        map.put("list",list);
        LimitKill limitKill=limitKillService.getOne(new QueryWrapper<LimitKill>().lambda().like(LimitKill::getLimitTime,""));
        if(limitKill.getEnable() != 0){
            if(redisTemplate.hasKey("MYLimitKill")){
                Long time= redisTemplate.getExpire("MYLimitKill");
                limitKill.setLimitTime(time);
            }else {
                Long time=limitKill.getLimitTime()/1000;
                redisTemplate.opsForValue().set("MYLimitKill","",time, TimeUnit.SECONDS);
            }
        }
        map.put("limitKill",limitKill);
        return new Result<>(HttpStatus.OK.value(),"success",map);
    }


}
