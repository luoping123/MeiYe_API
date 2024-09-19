package com.sct.meiye.controller;

import com.sct.meiye.entity.IntegralBill;
import com.sct.meiye.entity.Result;
import com.sct.meiye.entity.User;
import com.sct.meiye.entity.WalletBill;
import com.sct.meiye.service.IntegralBillService;
import com.sct.meiye.service.UserService;
import com.sct.meiye.service.WalletBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/sct/api/meiye/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private WalletBillService walletBillService;//钱包账单业务层
    @Autowired
    private IntegralBillService integralBillService;//积分账单业务层


    /**
     * 更新余额  和消费记录
     * @return
     */
    @PutMapping("/updateBalanceAndDetail")
    public Result<Object> updateBalanceAndDetail(@RequestBody WalletBill walletBill,@RequestBody User user){
        //计算余额并修改
        BigDecimal nowBalance=null;
        if("充值".equals(walletBill.getType())){
            nowBalance= new BigDecimal(String.valueOf(user.getBalance().add(walletBill.getMoney())));
        }else if("消费".equals(walletBill.getType())){
            nowBalance= new BigDecimal(String.valueOf(user.getBalance().subtract(walletBill.getMoney())));
        }
        user.setBalance(nowBalance);
        //修改用户信息
        Boolean updateUser=userService.updateById(user);
        //保存账单记录
        Boolean saveWalletBill=walletBillService.save(walletBill);
        return new Result<>(HttpStatus.OK.value(),"success",updateUser && saveWalletBill);
    }


    /**
     * 更新积分  和积分记录
     * @return
     */
    @PutMapping("/updateIntegralAndDetail")
    public Result<Object> updateIntegralAndDetail(@RequestBody IntegralBill integralBill,@RequestBody User user){
        //计算积分并修改
        Integer nowIntegral=user.getIntegral();
        if("获得".equals(integralBill.getType())){
            nowIntegral= user.getIntegral() + integralBill.getValue();
        }else if("兑换".equals(integralBill.getType())){
            nowIntegral= user.getIntegral() - integralBill.getValue();
        }
        user.setIntegral(nowIntegral);
        //修改用户信息
        Boolean updateUser=userService.updateById(user);
        //保存账单记录
        Boolean saveIntegralBill=integralBillService.save(integralBill);
        return new Result<>(HttpStatus.OK.value(),"success",updateUser && saveIntegralBill);
    }




}
