package com.sct.meiye.entity.dto;

import com.sct.meiye.entity.Goods;
import com.sct.meiye.entity.ServiceItem;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 商品详情 数据传输实体类型
 */
@Data//自动生成  get  set 方法
@Accessors(chain = true)
public class ServiceItemDetailsDto implements Serializable {

    private Long id;//主键id

    private List<SwiperDto> swiperDtoList;//轮播图

    private ServiceItem serviceItem;//商品信息

    private String content;//详情富文本

}
