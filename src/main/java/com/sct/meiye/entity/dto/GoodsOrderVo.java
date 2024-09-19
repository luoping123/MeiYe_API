package com.sct.meiye.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName goods_order
 */
@TableName(value ="goods_order")
@Data
@Accessors(chain = true)
public class GoodsOrderVo implements Serializable {

    private Long id;//主键id

    private String goodsOrderId;//订单编号

    private Long userId;//用户id

    private String openId;//微信登录用户为openId

    private List<GoodsVo> goodsVoList;//商品对象列表

    private Integer sumNumber;//总共几件商品

    private BigDecimal realPrice;//实际金额

    private String payType;//付款方式

    private String payTime;//付款时间

    private String orderStatus;//订单状态

    private String createTime;//创建订单时间

    private Integer isOutTime;//是否超时未支付

    private String receiverName;//收件人姓名

    private String receiverAddress;//收件地址

    private String receiverPhone;//收件人电话号码

    private String postalNumber;//快递单号

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}