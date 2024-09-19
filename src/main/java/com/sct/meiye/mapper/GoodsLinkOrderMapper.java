package com.sct.meiye.mapper;

import com.sct.meiye.entity.GoodsLinkOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sct.meiye.entity.dto.GoodsVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 15811
* @description 针对表【goods_link_order】的数据库操作Mapper
* @createDate 2022-04-27 17:58:36
* @Entity com.sct.meiye.entity.GoodsLinkOrder
*/
public interface GoodsLinkOrderMapper extends BaseMapper<GoodsLinkOrder> {

    @Select("select t1.id,t1.name,t1.image_src,t1.price,t2.attribute,t2.number " +
            "from goods as t1,goods_link_order as t2,goods_order as t3 " +
            "where t1.id=t2.goods_id and t2.order_id=t3.goods_order_id " +
            "and t3.goods_order_id= #{goodsOrderId}")
    List<GoodsVo> getGoodsVoList(String goodsOrderId);

}




