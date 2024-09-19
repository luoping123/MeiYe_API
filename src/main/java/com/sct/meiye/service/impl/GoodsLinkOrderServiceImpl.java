package com.sct.meiye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sct.meiye.entity.GoodsLinkOrder;
import com.sct.meiye.entity.Result;
import com.sct.meiye.entity.dto.GoodsVo;
import com.sct.meiye.service.GoodsLinkOrderService;
import com.sct.meiye.mapper.GoodsLinkOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 15811
* @description 针对表【goods_link_order】的数据库操作Service实现
* @createDate 2022-04-27 17:58:36
*/
@Service
public class GoodsLinkOrderServiceImpl extends ServiceImpl<GoodsLinkOrderMapper, GoodsLinkOrder>
    implements GoodsLinkOrderService{

    @Autowired
    private GoodsLinkOrderMapper mapper;

    @Override
    public Result<Object> getGoodsVoList(String goodsOrderId) {
        return new Result<>(HttpStatus.OK.value(), "success",mapper.getGoodsVoList(goodsOrderId));
    }

    public List<GoodsVo> getMyGoodsVoList(String goodsOrderId) {
        return mapper.getGoodsVoList(goodsOrderId);
    }
}




