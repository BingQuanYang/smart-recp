package com.smart.recp.service.support.config;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import com.smart.recp.service.support.props.SMSProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ybq
 */
@Configuration
public class SupportConfig {
    /**
     * 阿里云
     * 使用AK&SK初始化账号Client
     *
     * @return Client
     * @throws Exception
     */
    @Bean
    public Client client(SMSProperties smsProperties) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(smsProperties.getAccessKeyId())
                // 您的AccessKey Secret
                .setAccessKeySecret(smsProperties.getAccessKeySecret());
        // 访问的域名
        config.endpoint = smsProperties.getEndpoint();
        return new Client(config);
    }
}
