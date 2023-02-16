package com.smart.recp.service.support.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.smart.recp.common.redis.service.RedisService;
import com.smart.recp.service.support.props.SMSProperties;
import com.smart.recp.service.support.service.SMSService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author ybq
 */
@Service
public class SMSServiceImpl implements SMSService {

    @Resource
    SMSProperties smsProperties;
    @Resource
    RedisService redisService;
    @Resource
    Client client;

    @Override
    public String sendCode(String phone) {
        String code = RandomUtil.randomNumbers(6);
        redisService.setEx(phone, code, 2 * 60, TimeUnit.SECONDS);
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setSignName(smsProperties.getSignName());
        sendSmsRequest.setTemplateCode(smsProperties.getTemplateCode());
        sendSmsRequest.setPhoneNumbers(phone);
        sendSmsRequest.setTemplateParam(String.format("{\"code\":\"%s\"}", code));
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);
            SendSmsResponseBody body = sendSmsResponse.getBody();
            if (body.getCode().equals("OK")) {
                //成功
            }
            System.out.println(body);
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        return code;
    }
}
