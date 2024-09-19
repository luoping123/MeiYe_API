package com.sct.meiye.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sct.meiye.entity.*;
import com.sct.meiye.entity.dto.GoodsDetailsDto;
import com.sct.meiye.entity.dto.GoodsEvaluateDto;
import com.sct.meiye.entity.dto.ServiceItemDetailsDto;
import com.sct.meiye.entity.dto.SwiperDto;
import com.sct.meiye.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/sct/api/meiye/serviceItemDetails")
public class ServiceItemDetailsController {

    @Autowired
    private ServiceItemDetailsService serviceItemDetailsService;

    @Autowired
    private ServiceItemService serviceItemService;

    /**
     * 通过服务项目id  获取服务项目详情信息
     * @param serviceItemId
     * @return
     */
    @GetMapping("/getServiceItemDetailsByServiceItemId")
    public Result<Object> getServiceItemDetailsByServiceItemId(@RequestParam(value = "serviceItemId", required = false, defaultValue = "0") Long serviceItemId){
        ServiceItemDetailsDto serviceItemDetailsDto=new ServiceItemDetailsDto();
        //组装对象信息
        ServiceItemDetails serviceItemDetails=serviceItemDetailsService.getOne(new QueryWrapper<ServiceItemDetails>().lambda().eq(ServiceItemDetails::getServiceId,serviceItemId));
        serviceItemDetailsDto.setId(serviceItemDetails.getId());
        //配置轮播图
        String strSwiperUrl=serviceItemDetails.getSwiperUrl();
        String strSwiperType=serviceItemDetails.getSwiperType();
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
        serviceItemDetailsDto.setSwiperDtoList(swiperDtoList);
        //获取服务项目信息
        ServiceItem serviceItem=serviceItemService.getById(serviceItemDetails.getServiceId());
        serviceItemDetailsDto.setServiceItem(serviceItem);
        //设置富文本详情
        serviceItemDetailsDto.setContent(serviceItemDetails.getContent());
        return new Result<>(HttpStatus.OK.value(),"success",serviceItemDetailsDto);
    }



}
