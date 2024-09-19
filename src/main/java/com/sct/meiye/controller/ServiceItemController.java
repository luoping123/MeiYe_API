package com.sct.meiye.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sct.meiye.entity.Result;
import com.sct.meiye.entity.ServiceItem;
import com.sct.meiye.entity.ServiceItemCate;
import com.sct.meiye.service.ServiceItemCateService;
import com.sct.meiye.service.ServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sct/api/meiye/service_item")
public class ServiceItemController {

//    分页
//    @GetMapping("/list")
//    public Result<Object> list(
//            @RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
//            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
//            @RequestParam(value = "query", required = false, defaultValue = "") String query, String cid) {
//        Page<UniGoods> page = new Page<>(current, size);
//        QueryWrapper<UniGoods> wrapper = new QueryWrapper<>();
//        wrapper.lambda().like(UniGoods::getName, query);
//        if (!"".equals(cid)) wrapper.lambda().eq(UniGoods::getCatId, cid);
//        Page<UniGoods> uniGoodsPage = service.page(page, wrapper);
//        log.info("productPage:{}", uniGoodsPage);
//        return new Result<>(HttpStatus.OK.value(), Message.SUCCESS, uniGoodsPage);
//    }


    @Autowired
    private ServiceItemService serviceItemService;
    @Autowired
    private ServiceItemCateService serviceItemCateService;

    /**
     * 通过服务项目分类，获取服务项目列表 的列表
     * @return  [[服务项目,服务项目,],[],[]]
     */
    @GetMapping("/getservicelistlistbycate")
    public Result<Object> getServiceListListByCate(){
        //创建分页对象
        Page<ServiceItem> page = new Page<>(1, 10);
        //获取服务项目分类  列表
        List<ServiceItemCate> tabsList=serviceItemCateService.list();
        List<Map<String,Object>> serviceListList =new ArrayList<>();
        for(ServiceItemCate cate : tabsList){
            System.out.println("cate===================>>>>>>>>>>>"+cate.getId());
            //创建查询条件对象
            QueryWrapper<ServiceItem> wrapper = new QueryWrapper<>();
            if (!"".equals(cate.getId())) wrapper.lambda().eq(ServiceItem::getCateId, cate.getId());
            wrapper.lambda().orderByDesc(ServiceItem::getId);
            Page<ServiceItem> ServicesPage = serviceItemService.page(page, wrapper);
            Map<String,Object> map=new HashMap<>();
            //分页具体数据
            map.put("records",ServicesPage.getRecords());
            //总数据条数
            map.put("total",ServicesPage.getTotal());
            //当前页
            map.put("pageCurrent",1);
            //每页数据条数
            map.put("pageSize",10);
            serviceListList.add(map);
        }
        return new Result<>(HttpStatus.OK.value(),"success",serviceListList);
    }


    /**
     * 获取服务项目 分页数据
     * @param current  当前页
     * @param size  每页数据条数
     * @param keyName  服务名  模糊查询
     * @param cateId  分类id
     * @return
     */
    @GetMapping("/getpageservicelist")
    public Result<Object> getPageServiceListByCate(
                @RequestParam(value = "pageCurrent", required = false, defaultValue = "1") Integer current,
                @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer size,
                @RequestParam(value = "keyName", required = false, defaultValue = "") String keyName,
                @RequestParam(value = "cateId") Long cateId){
        //创建分页对象
        Page<ServiceItem> page = new Page<>(current, size);
        //创建查询条件对象
        QueryWrapper<ServiceItem> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(ServiceItem::getName, keyName);
        if (!"".equals(cateId)) wrapper.lambda().eq(ServiceItem::getCateId, cateId);
        Page<ServiceItem> ServicesPage = serviceItemService.page(page, wrapper);
        return new Result<>(HttpStatus.OK.value(), "success", ServicesPage);
    }

    /**
     * 通过id获取单个 服务项目
     * @param id
     * @return
     */
    @PostMapping("/getServiceItemById")
    public Result<Object> getServiceItemById(@RequestBody Long id){
        return new Result<>(HttpStatus.OK.value(),"success",serviceItemService.getById(id));
    }

}
