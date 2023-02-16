package com.smart.recp.service.support.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ybq
 */
@Component
@Data
@ConfigurationProperties(prefix = "sms")
public class SMSProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private String templateCode;
}
