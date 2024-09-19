package com.sct.meiye.controller;

import com.sct.meiye.entity.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sct/api/meiye/test")
public class TestController {

    @GetMapping("/gettest")
    public Result<Object> test(){
        return new Result<>(HttpStatus.OK.value(),"success","Hello sct");
    }

}
