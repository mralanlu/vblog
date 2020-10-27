package com.northbund.vblog.common.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sms")
@PropertySource(value = "classpath:sms.properties")
@Data
public class SmsConfigure {

    private String mainAccount;

    private String subAccount;

    private String password;

    private String baseUrl;

}
