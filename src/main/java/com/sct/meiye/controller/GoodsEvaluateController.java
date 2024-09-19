package com.sct.meiye.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sct.meiye.entity.Goods;
import com.sct.meiye.entity.GoodsEvaluate;
import com.sct.meiye.entity.Result;
import com.sct.meiye.entity.dto.GoodsEvaluateDto;
import com.sct.meiye.service.GoodsCateService;
import com.sct.meiye.service.GoodsEvaluateService;
import com.sct.meiye.service.GoodsService;
import com.sct.meiye.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/sct/api/meiye/goodsEvaluate")
public class GoodsEvaluateController {

    @Autowired
    private GoodsEvaluateService goodsEvaluateService;

    @Autowired
    private UserService userService;


    /**
     * 获取 好评、中评、差评个数
     * @param goodsId
     * @return
     */
    @GetMapping("/getGoodsEvaluateTypeCountByGoodsId")
    public Result<Object> getGoodsEvaluateTypeCountByGoodsId(@RequestParam(value = "goodsId", required = false) Long goodsId){
        Map<String,Object> map=new HashMap<>();
        Long good=goodsEvaluateService.count(new QueryWrapper<GoodsEvaluate>().lambda().eq(GoodsEvaluate::getGoodsId,goodsId).eq(GoodsEvaluate::getEvaluateType,"好评"));
        Long center=goodsEvaluateService.count(new QueryWrapper<GoodsEvaluate>().lambda().eq(GoodsEvaluate::getGoodsId,goodsId).eq(GoodsEvaluate::getEvaluateType,"中评"));
        Long low=goodsEvaluateService.count(new QueryWrapper<GoodsEvaluate>().lambda().eq(GoodsEvaluate::getGoodsId,goodsId).eq(GoodsEvaluate::getEvaluateType,"差评"));
        Long all=goodsEvaluateService.count(new QueryWrapper<GoodsEvaluate>().lambda().eq(GoodsEvaluate::getGoodsId,goodsId));
        map.put("all",all);
        map.put("good",good);
        map.put("center",center);
        map.put("lower",low);
        return new Result<>(HttpStatus.OK.value(),"success",map);
    }



    /**
     * 分页查询 商品评价  列表
     * @param current  当前页
     * @param size  每页几条数据
     * @param goodsId   商品id
     * @return
     */
    @GetMapping("/getGoodsEvaluateListByGoodsId")
    public Result<Object> getGoodsEvaluateListByGoodsId(
            @RequestParam(value = "pageCurrent", required = false, defaultValue = "1") Integer current,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "evaluateType", required = false, defaultValue = "") String evaluateType,
            @RequestParam(value = "goodsId", required = false) Long goodsId){

        //创建分页对象
        Page<GoodsEvaluate> page = new Page<>(current, size);
        //创建查询条件对象
        QueryWrapper<GoodsEvaluate> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(GoodsEvaluate::getEvaluateType,evaluateType);
        if (goodsId!=null) wrapper.lambda().eq(GoodsEvaluate::getGoodsId, goodsId);
        Page<GoodsEvaluate> goodsEvaluatePage = goodsEvaluateService.page(page, wrapper);
        //重新设置分页
        Page<GoodsEvaluateDto> goodsEvaluateDtoPage=new Page<>();

        List<GoodsEvaluateDto> geDtoList=new ArrayList<>();
        for(GoodsEvaluate ge: goodsEvaluatePage.getRecords()){
            GoodsEvaluateDto geDto=new GoodsEvaluateDto();
            geDto.setId(ge.getId());
            String strUrl=ge.getUrl();//接受字符串  图片、视频地址，‘;’分割
            //删除最后一个‘;’ 并 转换为字符串 列表
            List<String> urlList= Arrays.asList(strUrl.substring(0,strUrl.length()-1).split(";"));
            geDto.setUrlList(urlList);
            String strUrlType=ge.getUrlType();//接受字符串  图片or视频，image,video;
            //删除最后一个‘;’ 并 转换为字符串 列表
            List<String> urlTypeList=Arrays.asList(strUrlType.substring(0,strUrlType.length()-1).split(";"));
            geDto.setUrlTypeList(urlTypeList);
            geDto.setEvaluateDate(ge.getEvaluateDate());
            geDto.setEvaluateType(ge.getEvaluateType());
            geDto.setContent(ge.getContent());
            geDto.setEvaluateStar(ge.getEvaluateStar());
            geDto.setUser(userService.getById(ge.getUserId()));

            geDtoList.add(geDto);
        }
//        goodsEvaluatePage.setRecords(geDtoList);
        goodsEvaluateDtoPage.setRecords(geDtoList);
        goodsEvaluateDtoPage.setTotal(goodsEvaluatePage.getTotal());
        goodsEvaluateDtoPage.setSize(goodsEvaluatePage.getSize());
        goodsEvaluateDtoPage.setCurrent(goodsEvaluatePage.getCurrent());
        goodsEvaluateDtoPage.setOrders(goodsEvaluatePage.getOrders());
        goodsEvaluateDtoPage.setPages(goodsEvaluatePage.getPages());
        goodsEvaluateDtoPage.setOptimizeCountSql(true);
        goodsEvaluateDtoPage.setSearchCount(true);
        goodsEvaluateDtoPage.setCountId(goodsEvaluatePage.getCountId());
        goodsEvaluateDtoPage.setMaxLimit(goodsEvaluatePage.getMaxLimit());


        return new Result<>(HttpStatus.OK.value(), "success", goodsEvaluateDtoPage);
    }



}
