package com.sct.meiye.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * jwt 快速工具包
 *
 * @author sct
 * @version 1.0
 * @createTime 2022/4/26 14:35
 */
@Slf4j
@Component
public class JwtUtils {

    private static String jwtKey;

    private static Long expiration;

    private static JWTSigner signer;

    /**
     * 创建Token
     *
     * @param id 用户id
     * @return token
     */
    public static String create(String id) {
        new Date();
        return JWT.create()
                .setPayload("id", id)
                .setIssuedAt(DateUtil.date())
                .setExpiresAt(DateUtil.date(DateUtil.current() + expiration))
                .setSigner(initSigner())
                .sign();
    }

    /**
     * 初始化签名生成器
     *
     * @return 签名器
     */
    private static JWTSigner initSigner() {
        if (signer != null) return signer;
        signer = JWTSignerUtil.hs256(jwtKey.getBytes(StandardCharsets.UTF_8));
        return signer;
    }

    /**
     * 检查加签是否失效
     *
     * @param token token
     * @return boolean
     */
    private static boolean check(String token) {
        String item = token.replaceAll("\"", "");
        try {
            JWTValidator
                    .of(item)
                    .validateAlgorithm(JWTSignerUtil.hs256(jwtKey.getBytes(StandardCharsets.UTF_8)))
                    .validateDate(DateUtil.date(), 0);
        } catch (ValidateException e) {
            log.info("token失效:{}" + token);
            return false;
        }
        return true;
    }

    /**
     * 获取加签的值
     *
     * @param token token
     * @return 用户id
     */
    public static String getValue(String token) {
        if (token == null) return "";
        if (check(token))
            return JWT.of(token).getPayload("id").toString();
        return "";
    }

    public static String getValue(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                return getValue(cookie.getValue());
            }
        }
        return "";
    }

    @Value("${jwt.key}")
    public void setJwtKey(String jw) {
        jwtKey = jw;
    }

    @Value("${jwt.expiration}")
    public void setExpiration(Long ex) {
        expiration = ex;
    }
}
