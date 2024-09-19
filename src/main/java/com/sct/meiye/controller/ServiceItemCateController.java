package com.sct.meiye.controller;

import com.sct.meiye.entity.Result;
import com.sct.meiye.service.GridCateService;
import com.sct.meiye.service.ServiceItemCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sct/api/meiye/service_item_cate")
public class ServiceItemCateController {

    @Autowired
    private ServiceItemCateService serviceItemCateService;

    /**
     * 获取所有服务项目 分类数据
     * @return
     */
    @GetMapping("/getservicecatelist")
    public Result<Object> getServiceCateList(){
        return new Result<>(HttpStatus.OK.value(),"success",serviceItemCateService.list());
    }
}
