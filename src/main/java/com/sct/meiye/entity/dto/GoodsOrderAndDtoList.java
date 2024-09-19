package com.sct.meiye.entity.dto;

import com.sct.meiye.entity.GoodsOrder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data//自动生成  get  set 方法
@Accessors(chain = true)
public class GoodsOrderAndDtoList implements Serializable {

    private GoodsOrder goodsOrder;

    private List<GoodsDto> goodsDtoList;


}
