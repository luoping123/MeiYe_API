package com.sct.meiye.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sct.meiye.entity.IntegralBill;
import com.sct.meiye.entity.Result;
import com.sct.meiye.entity.User;
import com.sct.meiye.entity.WalletBill;
import com.sct.meiye.entity.dto.MyWalletVo;
import com.sct.meiye.service.GoodsCateService;
import com.sct.meiye.service.UserService;
import com.sct.meiye.service.WalletBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/sct/api/meiye/walletBill")
public class WalletBillController {

    @Autowired
    private WalletBillService walletBillService;

    @Autowired
    private UserService userService;

    /**
     *  根据用户id, 获取所有 钱包账单记录信息
     * @return
     */
    @GetMapping("/getWalletBillListByUserId")
    public Result<Object> getWalletBillListByUserId(
                        @RequestParam(value = "userId", required = false, defaultValue = "0") Long userId){
        return new Result<>(HttpStatus.OK.value(),"success",
                walletBillService.list(new QueryWrapper<WalletBill>().lambda().eq(WalletBill::getUserId,userId).orderByDesc(WalletBill::getDetailTime)));
    }

    /**
     *  充值或消费 从钱包
     * @param myWalletVo
     * @return
     */
    @PutMapping("/updateWallet")
    public Result<Object> updateWallet(@RequestBody MyWalletVo myWalletVo){
        System.out.println(myWalletVo.getBalance());
        System.out.println(myWalletVo.getUserId());

        BigDecimal balanceDouble=new BigDecimal(myWalletVo.getBalance()+"");
        User user=userService.getById(myWalletVo.getUserId());
        user.setBalance(balanceDouble);
        userService.updateById(user);
        return new Result<>(HttpStatus.OK.value(),"success",user.getBalance());
    }




}
