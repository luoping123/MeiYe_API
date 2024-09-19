package com.sct.meiye.controller;

import com.sct.meiye.entity.Result;
import com.sct.meiye.service.GoodsCateService;
import com.sct.meiye.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sct/api/meiye/goodsCate")
public class GoodsCateController {

    @Autowired
    private GoodsCateService goodsCateService;

    /**
     * 获取所有 商品分类信息
     * @return
     */
    @GetMapping("/getGoodsCateList")
    public Result<Object> getGoodsCateList(){
        return new Result<>(HttpStatus.OK.value(),"success",goodsCateService.list());
    }



}
