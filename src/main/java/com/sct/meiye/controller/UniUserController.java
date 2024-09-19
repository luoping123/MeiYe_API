package com.sct.meiye.controller;


import com.sct.meiye.entity.Result;
import com.sct.meiye.entity.dto.UniDetail;
import com.sct.meiye.service.IUniUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author sct
 * @version 1.0
 * @createTime 2022/4/26 14:35
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sct/api/meiye/login")
public class UniUserController {
    private final IUniUserService service;

    @PostMapping("/wxLogin")
    public Result<Object> login(@RequestBody @Validated UniDetail wxDetail) {
        System.out.println("wxDetail================>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(wxDetail);

        return service.login(wxDetail);
    }

    @PostMapping("/wxBeforeLogin")
    public Result<Object> beforeLogin(@RequestBody String code) {
        System.out.println("code================>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(code);


        service.saveCode(code);
        return new Result<>(HttpStatus.OK.value(), "success", "");
    }

    @PostMapping("/wxLoginOut")
    public Result<Object> wxLoginOut(@RequestParam String openId) {
        return service.loginOut(openId);
    }
}
