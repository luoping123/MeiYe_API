package com.sct.meiye.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sct.meiye.entity.*;
import com.sct.meiye.service.GoodsCateService;
import com.sct.meiye.service.GoodsService;
import com.sct.meiye.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sct/api/meiye/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsCateService cateService;



    /**
     * 获取所有商品信息
     * @return
     */
    @GetMapping("/getGoodsList")
    public Result<Object> getGoodsList(){
        return new Result<>(HttpStatus.OK.value(),"success",goodsService.list());
    }

    /**
     * 通过商品分类，获取商品列表 的列表
     * @return  [[商品,商品,],[],[]]
     */
    @GetMapping("/getGoodsListListByCate")
    public Result<Object> getGoodsListListByCate(){
        //创建分页对象
        Page<Goods> page = new Page<>(1, 10);
        //获取商品分类  列表
        List<GoodsCate> cateList=cateService.list();
        List<Map<String,Object>> goodsListList =new ArrayList<>();
        for(GoodsCate cate : cateList){
            System.out.println("cate===================>>>>>>>>>>>"+cate.getId());
            //创建查询条件对象
            QueryWrapper<Goods> wrapper = new QueryWrapper<>();
            if (!"".equals(cate.getId())) wrapper.lambda().eq(Goods::getGoodsCateId, cate.getId());
            Page<Goods> GoodsPage = goodsService.page(page, wrapper);
            Map<String,Object> map=new HashMap<>();
            //分页具体数据
            map.put("records",GoodsPage.getRecords());
            //总数据条数
            map.put("total",GoodsPage.getTotal());
            //当前页
            map.put("pageCurrent",1);
            //每页数据条数
            map.put("pageSize",10);
            goodsListList.add(map);
        }
        return new Result<>(HttpStatus.OK.value(),"success",goodsListList);
    }


    /**
     * 获取商品 分页数据
     * @param current  当前页
     * @param size  每页数据条数
     * @param keyName  商品名  模糊查询
     * @param cateId  分类id
     * @return
     */
    @GetMapping("/getPageGoodsList")
    public Result<Object> getPageGoodsList(
            @RequestParam(value = "pageCurrent", required = false, defaultValue = "1") Integer current,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "keyName", required = false, defaultValue = "") String keyName,
            @RequestParam(value = "cateId", required = false) Long cateId){

        //创建分页对象
        Page<Goods> page = new Page<>(current, size);
        //创建查询条件对象
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(Goods::getName, keyName);
        if (cateId!=null) wrapper.lambda().eq(Goods::getGoodsCateId, cateId);
        Page<Goods> GoodsPage = goodsService.page(page, wrapper);
        return new Result<>(HttpStatus.OK.value(), "success", GoodsPage);
    }

    /**
     * 获取商品 分页数据  用于搜索
     * @param current  当前页
     * @param size  每页数据条数
     * @param keyName  商品名  模糊查询
     * @return
     */
    @GetMapping("/getPageGoodsListByName")
    public Result<Object> getPageGoodsListByName(
            @RequestParam(value = "pageCurrent", required = false, defaultValue = "1") Integer current,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "keyName", required = false, defaultValue = "") String keyName){

        //创建分页对象
        Page<Goods> page = new Page<>(current, size);
        //创建查询条件对象
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(Goods::getName, keyName);
        Page<Goods> GoodsPage = goodsService.page(page, wrapper);
        return new Result<>(HttpStatus.OK.value(), "success", GoodsPage);
    } 



    /**
     * 通过分类id 获取商品信息列表
     * @param cateId
     * @return
     */
    @PostMapping("/getGoodsListByCateId")
    public Result<Object> getGoodsListByCateId(@RequestBody Long cateId){
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Goods::getGoodsCateId, cateId);
        return new Result<>(HttpStatus.OK.value(),"success",goodsService.list(wrapper));
    }

    /**
     * [用于支付成功后的推荐商品]获取商品信息列表
     * @param lowPrice
     * @param topPrice
     * @return
     */
    @GetMapping("/getGoodsListLikePrice")
    public Result<Object> getGoodsListLikePrice(@RequestParam(value = "lowPrice",required = false) String lowPrice,
                                                @RequestParam(value = "topPrice",required = false)String topPrice){
        BigDecimal lowPriceB = new BigDecimal(lowPrice);
        BigDecimal topPriceB = new BigDecimal(topPrice);
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.lambda().ge(Goods::getPrice,lowPriceB).le(Goods::getPrice,topPriceB);
        return new Result<>(HttpStatus.OK.value(),"success",goodsService.list(wrapper));
    }


    /**
     * 通过商品id  获取单个商品信息
     * @param goodsId
     * @return
     */
    @GetMapping("/getGoodsById")
    public Result<Object> getGoodsById(@RequestParam(value = "goodsId",required = false) Long goodsId){
        return new Result<>(HttpStatus.OK.value(),"success",goodsService.getById(goodsId));
    }


}
