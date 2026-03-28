package org.example.aijavacreate.controller;

import org.example.aijavacreate.common.BaseResponse;
import org.example.aijavacreate.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/")
    public BaseResponse<String> hello() {
        return ResultUtils.success("hello world");
    }
}
