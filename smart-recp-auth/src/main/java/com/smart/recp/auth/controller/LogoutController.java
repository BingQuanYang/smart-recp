package com.smart.recp.auth.controller;

import cn.hutool.json.JSONObject;
import com.smart.recp.auth.util.RequestUtils;
import com.smart.recp.common.core.constant.AuthConstant;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.common.redis.service.RedisService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author ybq
 */
@RestController
@RequestMapping("/oauth")
@Slf4j
public class LogoutController {

    @Resource
    RedisService redisService;

    @DeleteMapping("/logout")
    public RestResult<Boolean> logout() {
        JSONObject jsonObject = RequestUtils.getJwtPayload();
        // JWT唯一标识
        String jti = jsonObject.getStr(AuthConstant.JWT_JTI);
        // JWT过期时间戳
        long exp = jsonObject.getLong(AuthConstant.JWT_EXP);
        long currentTimeSeconds = System.currentTimeMillis() / 1000;
        if (exp < currentTimeSeconds) {
            // token已过期，无需加入黑名单
            log.error("token已过期，无需加入黑名单");
            return RestResult.success(true);
        }
        redisService.setEx(AuthConstant.TOKEN_BLACKLIST_PREFIX + jti, null, (exp - currentTimeSeconds), TimeUnit.SECONDS);
        return RestResult.success(true);
    }

}
