package com.sct.meiye.common.enums;

/**
 * 用户类型枚举
 * @author sct
 * @version 1.0
 * @createTime 2022/4/26 14:35
 */
public enum UserType {
    WECHAT_APPLET(1,"微信小程序用户"),

    ANDROID_USERS(2,"安卓用户"),

    APPLE_USERS(3,"苹果用户"),

    H5_USER(4,"H5用户"),

    OTHER(5,"其他用户");

    private final Integer key;

    private final String value;

    UserType(Integer key,String value){
        this.key= key;
        this.value= value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
