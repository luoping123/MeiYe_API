package com.sct.meiye.controller;

import com.sct.meiye.entity.Result;
import com.sct.meiye.service.GridCateService;
import com.sct.meiye.service.SwiperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sct/api/meiye/grid_cate")
public class GridCateController {

    @Autowired
    private GridCateService gridCateService;

    /**
     * 获取所有九宫格 分类数据
     * @return
     */
    @GetMapping("/getgrid_catelist")
    public Result<Object> getNoticeList(){
        return new Result<>(HttpStatus.OK.value(),"success",gridCateService.list());
    }
}
