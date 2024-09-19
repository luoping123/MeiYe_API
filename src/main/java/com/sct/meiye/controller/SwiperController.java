package com.sct.meiye.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sct.meiye.entity.Beautician;
import com.sct.meiye.entity.Result;
import com.sct.meiye.entity.Swiper;
import com.sct.meiye.service.SwiperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sct/api/meiye/swiper")
public class SwiperController {

    @Autowired
    private SwiperService swiperService;

    /**
     * 获取所有swiper轮播图数据
     * @return
     */
    @GetMapping("/getswiper")
    public Result<Object> getSwiperList(){
        return new Result<>(HttpStatus.OK.value(),"success",swiperService.list());
    }

    /**
     * 通过id获取单个 轮播图
     * @param id
     * @return
     */
    @PostMapping("/getSwiperById")
    public Result<Object> getSwiperById(@RequestBody Long id){
        return new Result<>(HttpStatus.OK.value(),"success",swiperService.getById(id));
    }

    /**
     * 保存轮播图
     * @param swiper
     * @return
     */
    @PostMapping("/saveSwiper")
    public Result<Object> saveSwiper(@RequestBody Swiper swiper){
        return new Result<>(HttpStatus.OK.value(),"success",swiperService.save(swiper));
    }

    /**
     * 修改轮播图
     * @param swiper
     * @return
     */
    @PutMapping("/updateSwiper")
    public Result<Object> updateSwiper(@RequestBody Swiper swiper){
        return new Result<>(HttpStatus.OK.value(),"success",swiperService.updateById(swiper));
    }

    /**
     * 删除轮播图
     * @param id
     * @return
     */
    @DeleteMapping("/deleteSwiper")
    public Result<Object> deleteSwiper(@RequestBody Long id){
        return new Result<>(HttpStatus.OK.value(),"success",swiperService.removeById(id));
    }

}
