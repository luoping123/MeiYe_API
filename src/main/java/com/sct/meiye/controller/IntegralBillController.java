package com.sct.meiye.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sct.meiye.entity.IntegralBill;
import com.sct.meiye.entity.Result;
import com.sct.meiye.entity.User;
import com.sct.meiye.entity.WalletBill;
import com.sct.meiye.entity.dto.SignInVo;
import com.sct.meiye.service.IntegralBillService;
import com.sct.meiye.service.UserService;
import com.sct.meiye.service.WalletBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sct/api/meiye/IntegralBill")
public class IntegralBillController {

    @Autowired
    private IntegralBillService integralBillService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserService userService;

    /**
     *  根据用户id, 获取所有 钱包账单记录信息
     * @return
     */
    @GetMapping("/getIntegralBillListByUserId")
    public Result<Object> getIntegralBillListByUserId(
                        @RequestParam(value = "userId", required = false, defaultValue = "0") Long userId){
        return new Result<>(HttpStatus.OK.value(),"success",
                integralBillService.list(new QueryWrapper<IntegralBill>().lambda().eq(IntegralBill::getUserId,userId).orderByDesc(IntegralBill::getDetailTime)));
    }

    /**
     * 获取签到 信息  从redis
     * @param signInKey
     * @return
     */
    @GetMapping("/getSignInFromRedis")
    public Result<Object> getSignInFromRedis(@RequestParam(value = "signInKey",required = false) String signInKey){
        System.out.println("getSignInFromRedis()=====>>>signInKey==================");
        System.out.println(signInKey);
        boolean isExist= redisTemplate.hasKey(signInKey);
        SignInVo signInVo=null;
        if(isExist){
            signInVo=new SignInVo();
            signInVo.setSignNow((String) redisTemplate.opsForHash().get(signInKey,"signNow"));
            signInVo.setSinNext((String) redisTemplate.opsForHash().get(signInKey,"signNext"));
            signInVo.setSinNumber((Integer) redisTemplate.opsForHash().get(signInKey,"signNumber"));
        }
        return  new Result<>(HttpStatus.OK.value(),"success",signInVo);
    }

    /**
     * 修改 签到信息
     * @param signInVo
     * @return
     */
    @PostMapping("/updateSignInToRedis")
    @Transactional//事务
    public Result<Object> updateSignInToRedis(@RequestBody SignInVo signInVo){
        System.out.println("signInVo========>>>>>");
        System.out.println(signInVo);
//        boolean isExist= redisTemplate.hasKey(signInKey);
//        if(isExist) redisTemplate.opsForHash().delete(signInKey);
//        redisTemplate.opsForHash().delete(signInKey,"signNow","signNext","signNumber");
        int values[] ={0,1,2,3,4,5,6,15};

        redisTemplate.opsForHash().put(signInVo.getSignInKey(),"signNow",signInVo.getSignNow());
        redisTemplate.opsForHash().put(signInVo.getSignInKey(),"signNext",signInVo.getSinNext());
        redisTemplate.opsForHash().put(signInVo.getSignInKey(),"signNumber",signInVo.getSinNumber());
        String [] strr=signInVo.getSignInKey().split("-");
        Long id=Long.parseLong(strr[1]);
        User user=userService.getById(id);
        user.setIntegral(user.getIntegral()+values[signInVo.getSinNumber()]);
        userService.updateById(user);
        System.out.println(user);
        return  new Result<>(HttpStatus.OK.value(),"success",user.getIntegral());
    }


}
