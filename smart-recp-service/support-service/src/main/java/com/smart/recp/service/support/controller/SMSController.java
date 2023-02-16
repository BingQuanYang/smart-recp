package com.smart.recp.service.support.controller;


import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.support.service.SMSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ybq
 */
@Api(tags = "阿里云SMS短信服务")
@RestController
@RequestMapping("/support/sms")
public class SMSController {

    @Resource
    SMSService smsService;

    @GetMapping("/send-code/{phone}/")
    @ApiOperation("短信发送验证码")
    @ApiImplicitParam(name = "phone", value = "手机号码")
    public RestResult<String> sendCode(@PathVariable("phone") String phone) {
        return RestResult.success(smsService.sendCode(phone));
    }
}
