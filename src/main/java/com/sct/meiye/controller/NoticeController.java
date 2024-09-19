package com.sct.meiye.controller;

import com.sct.meiye.entity.Result;
import com.sct.meiye.service.NoticeService;
import com.sct.meiye.service.SwiperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sct/api/meiye/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/getnotice")
    public Result<Object> getNoticeList(){
        return new Result<>(HttpStatus.OK.value(),"success",noticeService.getById(1));
    }

}
