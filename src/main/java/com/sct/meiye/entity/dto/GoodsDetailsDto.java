package com.sct.meiye.entity.dto;

import com.sct.meiye.entity.Goods;
import com.sct.meiye.entity.GoodsEvaluate;
import com.sct.meiye.entity.Swiper;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 商品详情 数据传输实体类型
 */
@Data//自动生成  get  set 方法
@Accessors(chain = true)
public class GoodsDetailsDto implements Serializable {

    private Long id;//主键id

//    private String swiperUrl;//轮播图图片或视频地址，‘;’分割

//    private String [] swiperUrlList;//轮播图图片或视频地址，‘;’分割

//    private String swiperType;//轮播图对应的类型（image、video）‘;’分割

//    private String [] swiperTypeList;//轮播图对应的类型（image、video）‘;’分割

    private List<SwiperDto> swiperDtoList;//轮播图

    private Integer priceType;//商品价格栏目 类型，0：原价栏目，1：限时抢购栏目

    private Goods goods;//商品信息

    private Integer freight;//运费

    private Long goodsEvaluateCount;// 评价总数

    private GoodsEvaluateDto upGoodsEvaluateDto;//置顶的商品评价（若无，则为最新的一个）

    private List<GoodsEvaluateDto> goodsEvaluateDtoList;//评论列表

    private String content;//详情富文本

}
