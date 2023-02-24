package com.smart.recp.gateway.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ybq
 */
@Data
@Component
@ConfigurationProperties(prefix = "whitelist")
public class WhiteListProperties {
    private List<String> urls;
}
