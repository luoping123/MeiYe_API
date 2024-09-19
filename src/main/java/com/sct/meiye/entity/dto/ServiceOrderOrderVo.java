package com.sct.meiye.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sct.meiye.entity.Beautician;
import com.sct.meiye.entity.ServiceItem;
import com.sun.javafx.runtime.async.BackgroundExecutor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @TableName service_order_order
 */
@TableName(value ="service_order_order")
@Data
@Accessors(chain = true)
public class ServiceOrderOrderVo implements Serializable {

    private String orderId;//主键订单号

    private String qrcodeNumber;//二维码号码

    private String remarks;//备注

    private String createTime;//创建订单时间

    private ServiceItem serviceItem;//服务项目

    private Long beauticianId;//美容师id

    private Beautician beautician;//美容师

    private Long uid;//客户id

    private String openId;//微信用户id

    private String endDate;//到期时间

    private String serviceType;//服务方式

    private String orderTime;//预约时间(只用于显示)

    private BigDecimal sumPrice;//订单总价

    private BigDecimal realPay;//实付款

    private String payType;//付款方式

    private String payTime;//付款时间

    private String orderStatus;//订单状态

    private Integer isOutTime;//是否超时未支付

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}