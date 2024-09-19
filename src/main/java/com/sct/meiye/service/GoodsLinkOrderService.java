package com.sct.meiye.service;

import com.sct.meiye.entity.GoodsLinkOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sct.meiye.entity.Result;
import com.sct.meiye.entity.dto.GoodsVo;

import java.util.List;

/**
* @author 15811
* @description 针对表【goods_link_order】的数据库操作Service
* @createDate 2022-04-27 17:58:36
*/
public interface GoodsLinkOrderService extends IService<GoodsLinkOrder> {

    Result<Object> getGoodsVoList(String goodsOrderId);
    List<GoodsVo> getMyGoodsVoList(String goodsOrderId);

}
