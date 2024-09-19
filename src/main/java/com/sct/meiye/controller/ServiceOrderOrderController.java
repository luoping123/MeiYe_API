package com.sct.meiye.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sct.meiye.entity.*;
import com.sct.meiye.entity.dto.ServiceOrderOrderVo;
import com.sct.meiye.service.*;
import com.sct.meiye.service.BeauticianService;
import com.sct.meiye.util.RandomIdUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/sct/api/meiye/service_order_order")
public class ServiceOrderOrderController {


    @Autowired
    private ServiceOrderOrderService orderOrderService;//订单表 service

    @Resource
    private RedisTemplate<String, Object> redisTemplate;//redis缓存操作模板工具类

    @Autowired
    private BeauticianTimeService beauticianTimeService;

    @Autowired
    private ServiceItemService serviceItemService;

    @Autowired
    private BeauticianService beauticianService;

    @Autowired
    private UserService userService;


    /**
     * 获取分页订单列表数据
     * @param current 当前页
     * @param size 每页多少条数据
     * @param type  订单类型
     * @return  serviceOrderVoPage 对象
     */
    @GetMapping("/getServiceOrderListPages")
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
                orderStatus="待使用";
                break;
            case 2:
                orderStatus= "待评价";
                break;
            case 3:
                orderStatus= "已完成";
                break;
            default:
                orderStatus= "待付款";
        }
        List<ServiceOrderOrderVo> serviceOrderOrderVoList=new ArrayList<>();
        //创建分页对象
        Page<ServiceOrderOrder> page = new Page<>(current, size);
        //创建查询条件对象
        QueryWrapper<ServiceOrderOrder> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ServiceOrderOrder::getOpenId,openId);
        if (!"-10".equals(type+"")) wrapper.lambda().eq(ServiceOrderOrder::getOrderStatus, orderStatus);
        Page<ServiceOrderOrder> serviceOrderOrderPage= orderOrderService.page(page, wrapper);
        for(ServiceOrderOrder soo:serviceOrderOrderPage.getRecords()){
            ServiceOrderOrderVo soov=new ServiceOrderOrderVo();
            //获取 服务项目对象
            ServiceItem serviceItem= serviceItemService.getById(soo.getServiceItemId());
            //重新组装 ServiceOrderOrderVo 对象
            soov.setOrderId(soo.getOrderId())
                    .setQrcodeNumber(soo.getQrcodeNumber())
                    .setRemarks(soo.getRemarks())
                    .setCreateTime(soo.getCreateTime())
                    .setServiceItem(serviceItem)
                    .setBeauticianId(soo.getBeauticianId())
                    .setUid(soo.getUid())
                    .setOpenId(soo.getOpenId())
                    .setEndDate(soo.getEndDate())
                    .setServiceType(soo.getServiceType())
                    .setOrderTime(soo.getOrderTime())
                    .setSumPrice(soo.getSumPrice())
                    .setRealPay(soo.getRealPay())
                    .setPayType(soo.getPayType())
                    .setPayTime(soo.getPayTime())
                    .setOrderStatus(soo.getOrderStatus());
            //获取未支付订单的时间
            if("待付款".equals(soov.getOrderStatus())){
                Long time= redisTemplate.getExpire(soo.getOrderId());
                soov.setIsOutTime( time < 0 ? 1 : 0 );//0：未超时，1：已经超时，不可支付
            }
            serviceOrderOrderVoList.add(soov);
        }
        Page<ServiceOrderOrderVo> serviceOrderVoPage = new Page<>(current, size);
        BeanUtils.copyProperties(serviceOrderOrderPage,serviceOrderVoPage);
        serviceOrderVoPage.setRecords(serviceOrderOrderVoList);
        System.out.println("=========================");
        System.out.println(serviceOrderVoPage);
        return new Result<>(HttpStatus.OK.value(), "success",serviceOrderVoPage);
    }



    /**
     * 通过id获取订单 信息
     * @param id
     * @return
     */
    @PostMapping("/getOrderInfoById")
    public Result<Object> getOrderInfoById(@RequestBody String id){
        ServiceOrderOrder soo = orderOrderService.getOne(new QueryWrapper<ServiceOrderOrder>().lambda().eq(ServiceOrderOrder::getOrderId,id));
        //获取服务项目 对象
        ServiceItem serviceItem = serviceItemService.getById(soo.getServiceItemId());
        //获取美容师 对象
        Beautician beautician = beauticianService.getById(soo.getBeauticianId());
        //重新组织 ServiceOrderOrderVo  对象信息
        ServiceOrderOrderVo serviceOrderOrderVo=new ServiceOrderOrderVo();
        serviceOrderOrderVo.setOrderId(soo.getOrderId())
                .setQrcodeNumber(soo.getQrcodeNumber())
                .setRemarks(soo.getRemarks())
                .setCreateTime(soo.getCreateTime())
                .setServiceItem(serviceItem)
                .setBeauticianId(soo.getBeauticianId())
                .setBeautician(beautician)
                .setUid(soo.getUid())
                .setOpenId(soo.getOpenId())
                .setEndDate(soo.getEndDate())
                .setServiceType(soo.getServiceType())
                .setOrderTime(soo.getOrderTime())
                .setSumPrice(soo.getSumPrice())
                .setRealPay(soo.getRealPay())
                .setPayType(soo.getPayType())
                .setPayTime(soo.getPayTime())
                .setOrderStatus(soo.getOrderStatus());
        return new Result<>(HttpStatus.OK.value(),"success",serviceOrderOrderVo);
    }

    /**
     * 【提交订单，待付款】保存订单信息
     * @param serviceOrderOrder
     * @return
     */
    @PostMapping("/saveOrdeBeginrOrder")
    @Transactional//事务
    public Result<Object> saveOrdeBeginrOrder(@RequestBody ServiceOrderOrder serviceOrderOrder){
        System.out.println("saveOrdeBeginrOrder()==================serviceOrderOrder===================");
        System.out.println(serviceOrderOrder);
        //生成订单编号
        serviceOrderOrder.setOrderId("MYO"+RandomIdUtil.generateUniqueKey());
        //将订单信息存入缓存，并设置30分钟后失效
        redisTemplate.opsForValue().set(serviceOrderOrder.getOrderId(),serviceOrderOrder,30L, TimeUnit.MINUTES);
        //将待支付订单信息存入数据库
        orderOrderService.save(serviceOrderOrder);
        //返回订单编号
        return new Result<>(HttpStatus.OK.value(),"success",serviceOrderOrder.getOrderId());
    }

    /**
     * 【获取待支付倒计时】获取待支付订单倒计时
     * @param serviceOrderId
     * @return  Long
     */
    @GetMapping("/getServiceOrderTimeByOrderId")
    public Result<Object> getServiceOrderTimeByOrderId(@RequestParam(value = "serviceOrderId",required = false) String serviceOrderId){
        Long time= redisTemplate.getExpire(serviceOrderId);
        return new Result<>(HttpStatus.OK.value(),"success",time);
    }

    /**
     * 【支付前的获取订单，出Redis】根据订单编号，从redis中获取订单信息   用于支付前的获取
     * @param serviceOrderId
     * @return
     */
    @GetMapping("/getServiceOrderFromRedisByOrderId")
    public Result<Object> getServiceOrderFromRedisByOrderId(@RequestParam(value = "serviceOrderId",required = false) String serviceOrderId){
        //获取订单信息
        ServiceOrderOrder serviceOrderOrder= (ServiceOrderOrder) redisTemplate.opsForValue().get(serviceOrderId);
        return new Result<>(HttpStatus.OK.value(),"success",serviceOrderOrder);
    }

    /**
     * 【支付成功，待使用】   修改订单信息
     * @param serviceOrderOrder
     * @return
     */
    @PutMapping("/updateServiceOrdeStatusBuySuccessByOrderId")
    @Transactional//事务
    public Result<Object> updateServiceOrdeStatusBuySuccessByOrderId(@RequestBody ServiceOrderOrder serviceOrderOrder){
        System.out.println("updateServiceOrdeStatusBuySuccessByOrderId()==================serviceOrderOrder===================");
        System.out.println(serviceOrderOrder);
        ServiceOrderOrder serviceOrderOrder1= (ServiceOrderOrder) redisTemplate.opsForValue().get(serviceOrderOrder.getOrderId());
        serviceOrderOrder1.setRealPay(serviceOrderOrder.getRealPay())
                .setPayType(serviceOrderOrder.getPayType())
                .setPayTime(serviceOrderOrder.getPayTime())
                .setOrderStatus(serviceOrderOrder.getOrderStatus())
                .setEndDate(serviceOrderOrder.getEndDate())
                .setQrcodeNumber(RandomIdUtil.generateUniqueKey());//生成二维码号码
        //更新订单信息
        orderOrderService.update(serviceOrderOrder1,new UpdateWrapper<ServiceOrderOrder>().lambda().eq(ServiceOrderOrder::getOrderId,serviceOrderOrder1.getOrderId()));
        System.out.println("serviceOrderOrder1()====================================");
        System.out.println(serviceOrderOrder1);

        //获取到美容师——时间 信息
        BeauticianTime beauticianTime=beauticianTimeService.getOne(
                new QueryWrapper<BeauticianTime>().lambda().eq(BeauticianTime::getBeauticianId,serviceOrderOrder1.getBeauticianId())
                        .eq(BeauticianTime::getBeauticianTime,serviceOrderOrder1.getServiceOrderDatetime()));
        System.out.println("beauticianTime()====================================");
        System.out.println(beauticianTime);
        int number=beauticianTime.getNumber();
        //让该美容师的该时间的可预约数-1
        beauticianTime.setNumber(number-1);

        beauticianTimeService.updateById(beauticianTime);
        if("余额支付".equals(serviceOrderOrder.getPayType())){
            User user= userService.getOne(new QueryWrapper<User>().lambda().eq(User::getOpenId,serviceOrderOrder.getOrderId()));
            BigDecimal B=new BigDecimal(serviceOrderOrder.getRealPay()+"");
            BigDecimal A=new BigDecimal(user.getBalance()+"");
            user.setBalance(A.subtract(B));
            userService.updateById(user);
        }


        return new Result<>(HttpStatus.OK.value(),"success","");
    }


    /**
     * 取消订单
     * @param serviceOrderId  订单编号
     * @return
     */
    @PutMapping("/cancelServiceOrder")
    public Result<Object> cancelServiceOrder(@RequestBody String serviceOrderId){
        System.out.println("serviceOrderId========================>>>>>>>>");
        System.out.println(serviceOrderId);
        //查询到  该订单信息
        ServiceOrderOrder serviceOrderOrder=orderOrderService.getOne(new QueryWrapper<ServiceOrderOrder>().lambda().eq(ServiceOrderOrder::getOrderId,serviceOrderId));
        //设置订单状态  为 已取消
        if(serviceOrderOrder!=null){
            serviceOrderOrder.setOrderStatus("已取消");
            //逻辑删除该订单
            orderOrderService.remove(new QueryWrapper<ServiceOrderOrder>().lambda().eq(ServiceOrderOrder::getOrderId,serviceOrderId));
        }
        return new Result<>(HttpStatus.OK.value(),"success","");
    }


}
