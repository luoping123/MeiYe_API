package com.sct.meiye.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sct.meiye.entity.Beautician;
import com.sct.meiye.entity.Coupon;
import com.sct.meiye.entity.Result;
import com.sct.meiye.service.BeauticianService;
import com.sct.meiye.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sct/api/meiye/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    /**
     * 获取所有  有剩余量的优惠券
     * @return
     */
    @GetMapping("/getCouponList")
    public Result<Object> getBeauticianList(){
        return new Result<>(HttpStatus.OK.value(),"success",couponService.list(new QueryWrapper<Coupon>().lambda().gt(Coupon::getNumber,0)));
    }


    /**
     * 通过id获取优惠券信息
     * @param id
     * @return
     */
    @GetMapping("/getCouponById")
    public Result<Object> getCouponById(@RequestParam(value = "id",required = false)Long id){
        return new Result<>(HttpStatus.OK.value(),"success",couponService.getById(id));
    }




}
