package com.sct.meiye.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.sct.meiye.common.enums.UserType;
import com.sct.meiye.entity.Result;
import com.sct.meiye.entity.User;
import com.sct.meiye.entity.dto.UniDetail;
import com.sct.meiye.entity.dto.UniOpenData;
import com.sct.meiye.entity.dto.UniUserDto;
import com.sct.meiye.entity.dto.UniUserVo;
import com.sct.meiye.service.IUniUserService;
import com.sct.meiye.service.UserService;
import com.sct.meiye.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author sct
 * @version 1.0
 * @createTime 2022/4/26 14:35
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UniUserServiceImpl implements IUniUserService {

    private static final String URL = "https://api.weixin.qq.com/sns/jscode2session?";

    private final RestTemplate restTemplate;

//    private final RedisTemplate<String, Object> redisTemplate;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private final UserService userService;

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.js_code}")
    private String js_code;

    @Override
    public Result<Object> login(UniDetail wx) {

        System.out.println("UniDetail==========================>>>>>>>>>>>>>>>>>");
        System.out.println(wx);

        if (!checkToken(wx.getCode()))
            return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "success", null);
        //获取存储在redis的code
        String code = String.valueOf(redisTemplate.opsForValue().get(wx.getCode()));

        System.out.println("code==========================>>>>>>>>>>>>>>>>>");
        System.out.println(code);

        //转换为实体
        UniOpenData openData = JSONUtil.toBean(code, UniOpenData.class);

        System.out.println("openData==========================>>>>>>>>>>>>>>>>>");
        System.out.println(openData);

        //解密信息
        String userInfo = this.getUserInfo(wx, openData.getSession_key());

        System.out.println("userInfo==========================>>>>>>>>>>>>>>>>>");
        System.out.println(userInfo);

        //转换为实体
        UniUserDto userDto = JSONUtil.toBean(userInfo, UniUserDto.class);

        System.out.println("userDto==========================>>>>>>>>>>>>>>>>>");
        System.out.println(userDto);

        //存储用户信息到本地
        User user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getOpenId, openData.getOpenid()));

        System.out.println("user==========================>>>>>>>>>>>>>>>>>");
        System.out.println(user);


        System.out.println("ObjectUtil.isNull(user)==========================>>>>>>>>>>>>>>>>>");
        System.out.println(ObjectUtil.isNull(user));
        //用户第一次使用存储用户数据
        if (ObjectUtil.isNull(user)) {
            System.out.println("用户第一次使用存储用户数据");
            userService.save(new User()
                    .setOpenId(openData.getOpenid())
                    .setType(UserType.WECHAT_APPLET.getKey())
                    .setAvatarUrl(userDto.getAvatarUrl())
                    .setGender(userDto.getGender())
                    .setName(userDto.getNickName())
                    .setAddress(userDto.getCountry() + userDto.getProvince() + userDto.getCity())
                    .setCreateTime(new Timestamp(System.currentTimeMillis()))
                    .setLastLoginTime(new Timestamp(System.currentTimeMillis())));


        } else {
            //否则更新
            userService.update(new User()
                            .setAvatarUrl(userDto.getAvatarUrl())
                            .setGender(userDto.getGender())
                            .setName(userDto.getNickName())
                            .setLastLoginTime(new Timestamp(System.currentTimeMillis()))
                            .setAddress(userDto.getCountry() + userDto.getProvince() + userDto.getCity())
                    , new UpdateWrapper<User>().lambda().eq(User::getOpenId, user.getOpenId()));
        }
        //生成token
        String token = genToken(openData.getOpenid());

        System.out.println("token==========================>>>>>>>>>>>>>>>>>");
        System.out.println(token);
        //获取User对象，用于获取userId
        User user1= userService.getOne(new QueryWrapper<User>().lambda().eq(User::getOpenId,openData.getOpenid()));
        UniUserVo userVo = new UniUserVo();
        BeanUtils.copyProperties(user1, userVo);
        userVo.setNickName(user1.getName());
        userVo.setToken(token);
        userVo.setUserId(user1.getId());

        System.out.println("userVo==========================>>>>>>>>>>>>>>>>>");
        System.out.println(userVo);

        //用户第一次使用存储用户数据
        if (ObjectUtil.isNull(user)) {
            //初始化配置签到
            String signInKey= "SignIn-" + userVo.getUserId();
            Date date = new Date();
            System.out.println(date);
            date.setDate(date.getDate()+1);
            System.out.println("date+1=================");
            System.out.println(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String datef = sdf.format(date);
            System.out.println(datef);

            redisTemplate.opsForHash().put(signInKey,"signNow","");
            redisTemplate.opsForHash().put(signInKey,"signNext",datef);
            redisTemplate.opsForHash().put(signInKey,"signNumber",0);
        }

        log.info("用户登录:用户openId:{},token:{}", user.getOpenId(), token);
        return new Result<>(HttpStatus.OK.value(), "success", userVo);
    }

    /**
     * 退出登录
     * @param openId
     * @return
     */
    @Override
    public Result<Object> loginOut(String openId) {
        redisTemplate.expire(openId, Duration.ZERO);
        return new Result<>(HttpStatus.OK.value(),"success",null);
    }

    /**
     * 将用户的code存储到服务器端  存储到redis缓存中
     *
     * @param code wx—code
     */
    @Override
    public void saveCode(String code) {
        String openData = this.getSessionKey(code);
        //登录临时数据1分钟后过期
        redisTemplate.opsForValue().set(code, openData, 1L, TimeUnit.MINUTES);
    }

    /**
     * 检测数据是否存在  Redis缓存中
     *
     * @param code code
     * @return 统一消息返回
     */
    private boolean checkToken(String code) {
        Object data = redisTemplate.opsForValue().get(code);
        return data != null && !data.equals("");
    }

    /**
     * 获取到用户隐私数据  SessionKey
     *
     * @param code code
     * @return 用户隐私数据json
     */
    private String getSessionKey(String code) {
        String url = URL + "grant_type=authorization_code&appid={1}&secret={2}&js_code={3}";
        return restTemplate.getForObject(url, String.class, appid, js_code, code);
    }

    /**
     * 通过微信的OpenId生成Token  存储到Redis缓存中，并设置3天后失效
     *
     * @param openId 微信openId
     * @return token 用户的token
     */
    private String genToken(String openId) {
        String token = JwtUtils.create(openId);
        //设置3天后Token失效    (key,hashKey,hashValue)
        redisTemplate.opsForHash().put(openId, "token", token);
        redisTemplate.expire(openId,Duration.ofDays(3L));
        return token;
    }

    /**
     * 解密获得  用户信息
     * @param wx
     * @param sessionKey
     * @return
     */
    private String getUserInfo(UniDetail wx, String sessionKey) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(wx.getEncryptedData());
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(wx.getIv());
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + 1;
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                return new String(resultByte, StandardCharsets.UTF_8);
            }
        } catch (NoSuchAlgorithmException | BadPaddingException | NoSuchPaddingException | InvalidParameterSpecException | IllegalBlockSizeException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchProviderException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
