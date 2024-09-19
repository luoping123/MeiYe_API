package com.sct.meiye.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 
 *  swiper 轮播图 数据传输实体类型
 */
@Data
@Accessors(chain = true)
public class SwiperDto implements Serializable {

    private Integer id;

//    private String imageSrc;//图片路径

//    private String openType;//跳转类型

//    private String navigatorUrl;//链接地址

    /**
     * 下面的属性用于商品详情  轮播图
     */
    private String type;//image  or  video
    private String url;//图片url

}