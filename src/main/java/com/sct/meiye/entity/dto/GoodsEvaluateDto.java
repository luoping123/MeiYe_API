package com.sct.meiye.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sct.meiye.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 商品评价 数据传输实体类型
 */
@Data//自动生成  get  set 方法
@Accessors(chain = true)
public class GoodsEvaluateDto implements Serializable {

    private Long id;//主键id

//    private String url;//图片、视频地址，‘;’分割

    private List<String> urlList;//图片、视频地址

//    private String urlType;//图片or视频，image;video

    private List<String> urlTypeList;//图片or视频，image,video

//    @JsonFormat(pattern = "yyyy-MM-dd")
    private String evaluateDate;//评价时间

    private String evaluateType;//好评、中评、差评

    private String content;//评价内容

    private Integer evaluateStar;//评价星级

    private User user;//评价用户

}