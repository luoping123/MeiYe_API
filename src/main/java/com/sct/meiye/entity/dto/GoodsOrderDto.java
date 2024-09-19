package com.sct.meiye.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sct.meiye.entity.Goods;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * 商品订单 数据传输实体类型
 */
@Data//自动生成  get  set 方法
@Accessors(chain = true)
public class GoodsOrderDto implements Serializable {

    private Long id;//主键id

    private String goodsOrderId;//订单编号

    private Long userId;//用户id

    private String openId;//微信登录用户为openId

    private String redisKey;//5分钟有效时间，并且退出ConfirmOrder.vue页面后设置失效

//    private String goodsIdList;//商品id;商品id;

    private GoodsDto goods;//单个商品

    private List<Goods> goodsList;

    private List<GoodsDto> goodsDtoList;//商品数组

    private String remark;//留言

    private Integer intergralSum;//订单总积分

    private BigDecimal priceSum;// 订单总金额

    private BigDecimal realPrice;//实际金额

    private String useCoupon;//优惠券减少金额

    private String orderStatus;//订单状态

    private String payType;// 付款方式

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String payTime;//付款时间

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String payDeadline;//付款截止时间，到这个时间后未付款，订单失效，用于倒计时；；【目前用redis实现，更简便】

    private BigDecimal freight;//运费

    private String receiverName;//收件人姓名

    private String receiverAddress;//收件地址

    private String receiverPhone;//收件人电话号码

    private String postalNumber;//快递单号

}