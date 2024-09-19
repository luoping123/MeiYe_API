package com.sct.meiye.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sct.meiye.entity.*;
import com.sct.meiye.entity.dto.*;
import com.sct.meiye.service.*;
import com.sct.meiye.util.RandomIdUtil;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/sct/api/meiye/goodsOrder")
public class GoodsOrderController {

    @Autowired
    private GoodsOrderService goodsOrderService;

    @Autowired
    private GoodsService goodsService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private GoodsLinkOrderService goodsLinkOrderService;

    @Autowired
    private UserService userService;


    /**
     * 获取分页订单列表数据
     * @param current
     * @param size
     * @param type
     * @return
     */
    @GetMapping("/getOrderListPages")
    public Result<Object> getOrderListPages(
                @RequestParam(value = "page", required = false, defaultValue = "1") Integer current,
                @RequestParam(value = "limit", required = false, defaultValue = "10") Integer size,
                @RequestParam(value = "type",required = false,defaultValue = "-10") Integer type,
                @RequestParam(value = "openId", required = false) String openId){
        System.out.println("pageCurrent=======>>>>>>>>>>>>>");
        System.out.println(current);
        System.out.println("pageSize=======>>>>>>>>>>>>>");
        System.out.println(size);
        System.out.println("type=======>>>>>>>>>>>>>");
        System.out.println(type);
        String orderStatus="";
        switch(type){
            case 0:
                orderStatus="待付款";
                break;
            case 1:
                orderStatus="待发货";
                break;
            case 2:
                orderStatus= "待收货";
                break;
            case 3:
                orderStatus= "待评价";
                break;
            case 4:
                orderStatus= "已完成";
                break;
            default:
                orderStatus= "待付款";
        }
        List<GoodsOrderVo> goodsOrderVoList=new ArrayList<>();
        //创建分页对象
        Page<GoodsOrder> page = new Page<>(current, size);
        //创建查询条件对象
        QueryWrapper<GoodsOrder> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(GoodsOrder::getOpenId,openId);
        if (!"-10".equals(type+"")) wrapper.lambda().eq(GoodsOrder::getOrderStatus, orderStatus);
        Page<GoodsOrder> goodsOrderPage= goodsOrderService.page(page, wrapper);
        for(GoodsOrder go:goodsOrderPage.getRecords()){
            GoodsOrderVo gov=new GoodsOrderVo();
            gov.setId(go.getId())
                    .setGoodsOrderId(go.getGoodsOrderId())
                    .setUserId(go.getUserId())
                    .setOpenId(go.getOpenId())
                    .setCreateTime(go.getCreateTime())
                    .setOrderStatus(go.getOrderStatus())
                    .setRealPrice(go.getRealPrice())
                    .setGoodsVoList((List<GoodsVo>) goodsLinkOrderService.getMyGoodsVoList(go.getGoodsOrderId()));
            int num=0;
            for(GoodsVo goodsVo :gov.getGoodsVoList()){
                num+=goodsVo.getNumber();
            }
            //设置订单商品总量
            gov.setSumNumber(num);
            //获取未支付订单的时间
            if("待付款".equals(gov.getOrderStatus())){
                Long time= redisTemplate.getExpire(go.getGoodsOrderId());
                gov.setIsOutTime( time < 0 ? 1 : 0 );//0：未超时，1：已经超时，不可支付
            }
            goodsOrderVoList.add(gov);
        }
        Page<GoodsOrderVo> goodsOrderVoPage = new Page<>(current, size);
        BeanUtils.copyProperties(goodsOrderPage,goodsOrderVoPage);
        goodsOrderVoPage.setRecords(goodsOrderVoList);
        return new Result<>(HttpStatus.OK.value(), "success",goodsOrderVoPage);
    }


    /**
     * 【订单列表】根据用户id，获取订单列表 [[],[],[],[]]
     * @param userId 普通用户id
     * @param openId 微信登录用户id
     * @return
     */
    @GetMapping("/getGoodsOrderListListByUserIdAndByOrderType")
    public Result<Object> getGoodsOrderListListByUserIdAndByOrderType(
                                @RequestParam(value = "userId", required = false) Long userId,
                                @RequestParam(value = "openId", required = false) String openId){
        String [] statusList="待付款;待发货;待收货;待评价;已完成;退款售后".split(";");
        List<List<GoodsOrderDto>> goodsOrderDtoListList=new ArrayList<>();
        for(String status :statusList){
            List<GoodsOrderDto> goodsOrderDtoList=new ArrayList<>();
            QueryWrapper<GoodsOrder> queryWrapper= new QueryWrapper<GoodsOrder>();
            if(userId!=null){
                queryWrapper.lambda().eq(GoodsOrder::getUserId,userId).eq(GoodsOrder::getOrderStatus,status);
            }else if(openId!=null){
                queryWrapper.lambda().eq(GoodsOrder::getOpenId,openId).eq(GoodsOrder::getOrderStatus,status);
            }
            List<GoodsOrder> goodsOrderList=goodsOrderService.list(queryWrapper);
            for(GoodsOrder goodsOrder: goodsOrderList){
                goodsOrderDtoList.add((GoodsOrderDto)this.getGoodsOrderByOrderId(goodsOrder.getGoodsOrderId()).getData());
            }
            goodsOrderDtoListList.add(goodsOrderDtoList);
        }
        return new Result<>(HttpStatus.OK.value(),"success",goodsOrderDtoListList);
    }


    /**
     * 【查询单个订单】根据订单编号，获取订单信息
     * @param goodsOrderId
     * @return
     */
    @GetMapping("/getGoodsOrderByOrderId")
    public Result<Object> getGoodsOrderByOrderId(@RequestParam(value = "goodsOrderId", required = false) String goodsOrderId){
        GoodsOrder goodsOrder=goodsOrderService.getOne(new QueryWrapper<GoodsOrder>().lambda().eq(GoodsOrder::getGoodsOrderId,goodsOrderId));
        GoodsOrderVo goodsOrderVo=new GoodsOrderVo();
        //copy 属性
        BeanUtils.copyProperties(goodsOrder,goodsOrderVo);
        //设置商品列表
        goodsOrderVo.setGoodsVoList((List<GoodsVo>) goodsLinkOrderService.getMyGoodsVoList(goodsOrder.getGoodsOrderId()));
        int num=0;
        for(GoodsVo goodsVo :goodsOrderVo.getGoodsVoList()){
            num+=goodsVo.getNumber();
        }
        //设置订单商品总量
        goodsOrderVo.setSumNumber(num);
        return new Result<>(HttpStatus.OK.value(),"success",goodsOrderVo);
    }



    /**
     * 【提交订单前，入redis】将初步订单信息存入 redis数据库，便于订单提交的数据收集
     * @param goodsOrderDto
     * @return
     */
    @PostMapping("/saveGoodsOrderToRedis")
    public Result<Object> saveGoodsOrderToRedis(@RequestBody GoodsOrderDto goodsOrderDto){
        System.out.println("goodsOrderDto====================================================");
        System.out.println(goodsOrderDto);
//        redisTemplate.expire(openId, Duration.ZERO);
        //设置5分钟后失效   key:   token+‘;’+goods.id  value: GoodsOrderDto
//        redisTemplate.opsForValue().set(goodsOrderDto.getRedisKey(), "123456", 5L, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(goodsOrderDto.getRedisKey(), goodsOrderDto, 5L, TimeUnit.MINUTES);
        return new Result<>(HttpStatus.OK.value(),"success","");
    }


    /**
     * 从Redis数据库中获取订单信息
     * @param redisKey
     * @return
     */
    @GetMapping("/getGoodsOrderFromRedis")
    public Result<Object> getGoodsOrderFromRedis(@RequestParam(value = "redisKey",required = false) String redisKey){
        System.out.println("redisKey======================>>>>>>>>>>>>");
        System.out.println(redisKey);
        GoodsOrderDto goodsOrderDto= (GoodsOrderDto) redisTemplate.opsForValue().get(redisKey);
        System.out.println("goodsOrderDto======================>>>>>>>>>>>>");
        System.out.println(goodsOrderDto);
        return new Result<>(HttpStatus.OK.value(),"success",goodsOrderDto);
    }


    /**
     * 设置订单信息失效
     * @param redisKey
     * @return
     */
    @DeleteMapping("/deleteGoodsOrderToRedis")
    public Result<Object> deleteGoodsOrderToRedis(@RequestParam(value = "redisKey",required = false) String redisKey){

        //设置订单信息失效   key:   token+‘;’+goods.id  value: GoodsOrderDto
        redisTemplate.expire(redisKey, Duration.ZERO);
        return new Result<>(HttpStatus.OK.value(),"success","");
    }


    /**
     * 【待支付 入Redis、Mysql】根据传进来的对象  提交（保存）订单信息，到redis和mysql中，并设置支付倒计时  提交订单，但还未支付
     * @param goodsOrderAndDtoList 订单信息  +  订单中的商品列表（包含商品的数量，属性等）
     * @return 订单编号
     */
    @PostMapping("/saveGoodsOrder")
    @Transactional//事务
    public Result<Object> saveGoodsOrder(@RequestBody GoodsOrderAndDtoList goodsOrderAndDtoList){
        System.out.println("goodsOrderAndDtoList========================>>>>>>");
        System.out.println(goodsOrderAndDtoList);
        //取出订单对象
        GoodsOrder goodsOrder=goodsOrderAndDtoList.getGoodsOrder();
        //取出 商品传输对象  list
        List<GoodsDto> goodsDtoList=goodsOrderAndDtoList.getGoodsDtoList();
        System.out.println("goodsOrder========================>>>>>>");
        System.out.println(goodsOrder);
        System.out.println("goodsDtoList========================>>>>>>");
        System.out.println(goodsDtoList);
        //生成订单编号
        String goodsOrderId="MYG"+RandomIdUtil.generateUniqueKey();
        //设置订单编号
        goodsOrder.setGoodsOrderId(goodsOrderId);
        //将订单的商品信息存入redis中
        redisTemplate.opsForHash().put(goodsOrderId,"goodsDtoList",goodsDtoList);
        //将订单信息存入redis中
        redisTemplate.opsForHash().put(goodsOrderId,"goodsOrder",goodsOrder);
        //设置30分钟后失效
        redisTemplate.expire(goodsOrder.getGoodsOrderId(),Duration.ofMinutes(30L));
        //将“待付款”状态的订单信息 存入 mysql数据库中
        goodsOrderService.save(goodsOrder);
        //遍历商品信息并存入mysql数据库中
        for (GoodsDto goodsDto : goodsDtoList) {
            GoodsLinkOrder goodsLinkOrder = new GoodsLinkOrder();
            goodsLinkOrder.setGoodsId(goodsDto.getId())
                    .setOrderId(goodsOrder.getGoodsOrderId())
                    .setNumber(goodsDto.getGoodsNum())
                    .setAttribute(goodsDto.getAttribute());
            goodsLinkOrderService.save(goodsLinkOrder);
        }
        //返回订单编号 goodsOrderId
        return new Result<>(HttpStatus.OK.value(),"success",goodsOrderId);
    }


    /**
     * 【获取待支付倒计时】获取待支付订单倒计时
     * @param goodsOrderId
     * @return  Long
     */
    @GetMapping("/getGoodsOrderTimeByOrderId")
    public Result<Object> getGoodsOrderTimeByOrderId(@RequestParam(value = "goodsOrderId",required = false) String goodsOrderId){
        Long time= redisTemplate.getExpire(goodsOrderId);
        return new Result<>(HttpStatus.OK.value(),"success",time);
    }


    /**
     * 【支付前的获取订单，出Redis】根据订单编号，从redis中获取订单信息   用于支付前的获取
     * @param goodsOrderId
     * @return
     */
    @GetMapping("/getGoodsOrderFromRedisByOrderId")
    public Result<Object> getGoodsOrderFromRedisByOrderId(@RequestParam(value = "goodsOrderId",required = false) String goodsOrderId){
        //获取订单信息
        GoodsOrder goodsOrder= (GoodsOrder) redisTemplate.opsForHash().get(goodsOrderId,"goodsOrder");
        return new Result<>(HttpStatus.OK.value(),"success",goodsOrder);
    }

    /**
     * 【支付成功后，入Mysql】将更新订单状态信息 为 待发货
     * @param goodsOrder
     * @return
     */
    @PutMapping("/updateGoodsOrdeStatusBuySuccessByOrderId")
    @Transactional//事务
    public Result<Object> updateGoodsOrdeStatusBuySuccessByOrderId(@RequestBody GoodsOrder goodsOrder){
        System.out.println(" updateGoodsOrdeStatusBuySuccessByOrderId==goodsOrder=======================================");
        System.out.println(goodsOrder);
        //修改订单信息
        goodsOrderService.update(goodsOrder,new QueryWrapper<GoodsOrder>().lambda().eq(GoodsOrder::getGoodsOrderId,goodsOrder.getGoodsOrderId()));
        if("余额支付".equals(goodsOrder.getPayType())){
           User user= userService.getOne(new QueryWrapper<User>().lambda().eq(User::getOpenId,goodsOrder.getOpenId()));
            BigDecimal B=new BigDecimal(goodsOrder.getRealPrice()+"");
            BigDecimal A=new BigDecimal(user.getBalance()+"");
            user.setBalance(A.subtract(B));
            userService.updateById(user);
        }

        return new Result<>(HttpStatus.OK.value(),"success","");
    }

    /**
     * 取消订单
     * @param goodsOrderId  订单编号
     * @return
     */
    @PutMapping("/cancelGoodsOrder")
    public Result<Object> cancelGoodsOrder(@RequestBody String goodsOrderId){
        System.out.println("goodsOrderId========================>>>>>>>>");
        System.out.println(goodsOrderId);
        //查询到  该订单信息
        GoodsOrder goodsOrder=goodsOrderService.getOne(new QueryWrapper<GoodsOrder>().lambda().eq(GoodsOrder::getGoodsOrderId,goodsOrderId));
        //设置订单状态  为 已取消
        if(goodsOrder!=null){
            goodsOrder.setOrderStatus("已取消");
            //逻辑删除该订单
            goodsOrderService.remove(new QueryWrapper<GoodsOrder>().lambda().eq(GoodsOrder::getGoodsOrderId,goodsOrderId));
        }
        return new Result<>(HttpStatus.OK.value(),"success","");
    }


//
//
//    /**
//     * 根据传进来的对象  更新（修改）订单信息（订单状态等）
//     * @param goodsOrder
//     * @return
//     */
//    @PutMapping("/updateGoodsOrder")
//    public Result<Object> updateGoodsOrder(@RequestBody GoodsOrder goodsOrder){
//        return new Result<>(HttpStatus.OK.value(),"success",goodsOrderService.save(goodsOrder));
//    }






}
