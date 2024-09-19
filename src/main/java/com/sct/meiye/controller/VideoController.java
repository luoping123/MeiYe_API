package com.sct.meiye.controller;

import com.sct.meiye.entity.Result;
import com.sct.meiye.entity.Video;
import com.sct.meiye.service.NoticeService;
import com.sct.meiye.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sct/api/meiye/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/getVideoList")
    public Result<Object> getVideoList(@RequestBody String info){
        return new Result<>(HttpStatus.OK.value(),"success",videoService.list());
    }

}
