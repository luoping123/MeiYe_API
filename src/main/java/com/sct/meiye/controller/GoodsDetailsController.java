package com.sct.meiye.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sct.meiye.entity.*;
import com.sct.meiye.entity.dto.GoodsDetailsDto;
import com.sct.meiye.entity.dto.GoodsEvaluateDto;
import com.sct.meiye.entity.dto.SwiperDto;
import com.sct.meiye.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/sct/api/meiye/goodsDetails")
public class GoodsDetailsController {

    @Autowired
    private GoodsDetailsService goodsDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsEvaluateService goodsEvaluateService;


    /**
     * 获取所有 商品详情信息
     * @return
     */
    @GetMapping("/getGoodsDetailsList")
    public Result<Object> getGoodsDetailsList(){
        return new Result<>(HttpStatus.OK.value(),"success",goodsDetailsService.list());
    }

    /**
     * 通过商品id  获取商品详情信息
     * @param goodsId
     * @return
     */
    @GetMapping("/getGoodsDetailsByGoodsId")
    public Result<Object> getGoodsDetailsByGoodsId(@RequestParam(value = "goodsId", required = false, defaultValue = "0") Long goodsId){
        GoodsDetailsDto goodsDetailsDto=new GoodsDetailsDto();
        //组装对象信息
        GoodsDetails goodsDetails=goodsDetailsService.getOne(new QueryWrapper<GoodsDetails>().lambda().eq(GoodsDetails::getGoodsId,goodsId));
        goodsDetailsDto.setId(goodsDetails.getId());
        //配置轮播图
        String strSwiperUrl=goodsDetails.getSwiperUrl();
        String strSwiperType=goodsDetails.getSwiperType();
        String [] strSwiperUrlList=strSwiperUrl.substring(0,strSwiperUrl.length()-1).split(";");
        String [] strSwiperTypeList=strSwiperType.substring(0,strSwiperType.length()-1).split(";");
        List<SwiperDto> swiperDtoList=new ArrayList<>();
        for(int i=0;i<strSwiperUrlList.length;i++){
            SwiperDto swiperDto=new SwiperDto();
            swiperDto.setId(i);
            swiperDto.setType(strSwiperTypeList[i]);
            swiperDto.setUrl(strSwiperUrlList[i]);
            swiperDtoList.add(swiperDto);
        }
        goodsDetailsDto.setSwiperDtoList(swiperDtoList);
        //获取商品信息
        Goods goods=goodsService.getById(goodsDetails.getGoodsId());
        goodsDetailsDto.setGoods(goods);
        goodsDetailsDto.setFreight(goodsDetails.getFreight());
        //获取评论总条数
        Long count=goodsEvaluateService.count(new QueryWrapper<GoodsEvaluate>().lambda().eq(GoodsEvaluate::getGoodsId,goodsId));
        goodsDetailsDto.setGoodsEvaluateCount(count);
        //获取置顶评论
        GoodsEvaluate up=goodsEvaluateService.getOne(new QueryWrapper<GoodsEvaluate>().lambda().eq(GoodsEvaluate::getIsUp,1).eq(GoodsEvaluate::getGoodsId,goodsId));
        List<GoodsEvaluate> evaluates= goodsEvaluateService.list(new QueryWrapper<GoodsEvaluate>().lambda().eq(GoodsEvaluate::getGoodsId,goodsId).orderByDesc(GoodsEvaluate::getEvaluateDate));
        if(up==null){
            //若无置顶评论，获取最近的一个
           up=evaluates.get(0);
        }
        //配置 商品置顶评价 数据传输对象
        GoodsEvaluateDto upGoodsEvaluateDto=new GoodsEvaluateDto();
        upGoodsEvaluateDto.setId(up.getId());
        String strUrl1=up.getUrl();//接受字符串  图片、视频地址，‘;’分割
        //删除最后一个‘;’ 并 转换为字符串 列表
        List<String> urlList1= Arrays.asList(strUrl1.substring(0,strUrl1.length()-1).split(";"));
        upGoodsEvaluateDto.setUrlList(urlList1);
        String strUrlType1=up.getUrlType();//接受字符串  图片or视频，image,video;
        //删除最后一个‘;’ 并 转换为字符串 列表
        List<String> urlTypeList1=Arrays.asList(strUrlType1.substring(0,strUrlType1.length()-1).split(";"));
        upGoodsEvaluateDto.setUrlTypeList(urlTypeList1);
        upGoodsEvaluateDto.setEvaluateDate(up.getEvaluateDate());
        upGoodsEvaluateDto.setEvaluateType(up.getEvaluateType());
        upGoodsEvaluateDto.setContent(up.getContent());
        upGoodsEvaluateDto.setEvaluateStar(up.getEvaluateStar());
        upGoodsEvaluateDto.setUser(userService.getById(up.getUserId()));
        //设置置顶评论
        goodsDetailsDto.setUpGoodsEvaluateDto(upGoodsEvaluateDto);
        //设置富文本详情
        goodsDetailsDto.setContent(goodsDetails.getContent());
        return new Result<>(HttpStatus.OK.value(),"success",goodsDetailsDto);
    }



}
