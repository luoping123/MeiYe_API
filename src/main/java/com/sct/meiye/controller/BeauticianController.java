package com.sct.meiye.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sct.meiye.entity.Beautician;
import com.sct.meiye.entity.Result;
import com.sct.meiye.entity.ServiceItem;
import com.sct.meiye.service.BeauticianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sct/api/meiye/beautician")
public class BeauticianController {

    @Autowired
    private BeauticianService beauticianService;

    /**
     * 获取所有美容师列表
     * @return
     */
    @GetMapping("/getBeauticianList")
    public Result<Object> getBeauticianList(@RequestParam(value = "beauticianTime",required = false)String beauticianTime){
        return new Result<>(HttpStatus.OK.value(),"success",beauticianService.getBeauticianListByTime(beauticianTime));
    }


    /**
     * 通过id获取单个 美容师
     * @param id
     * @return
     */
    @PostMapping("/getBeauticianById")
    public Result<Object> getBeauticianById(@RequestBody Long id){
        return new Result<>(HttpStatus.OK.value(),"success",beauticianService.getById(id));
    }

    /**
     * 保存美容师
     * @param beautician
     * @return
     */
    @PostMapping("/saveBeautician")
    public Result<Object> saveBeautician(@RequestBody Beautician beautician){
        return new Result<>(HttpStatus.OK.value(),"success",beauticianService.save(beautician));
    }

    /**
     * 修改美容师
     * @param beautician
     * @return
     */
    @PutMapping("/updateBeautician")
    public Result<Object> updateBeautician(@RequestBody Beautician beautician){
        return new Result<>(HttpStatus.OK.value(),"success",beauticianService.updateById(beautician));
    }

    /**
     * 删除美容师
     * @param id
     * @return
     */
    @DeleteMapping("/deleteBeautician")
    public Result<Object> deleteBeautician(@RequestBody Long id){
        return new Result<>(HttpStatus.OK.value(),"success",beauticianService.removeById(id));
    }



}
