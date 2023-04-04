package org.dows.auth.api.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "dows.pay")
public class PayClientConfig {
    private List<PayClientProperties> clientConfigs;
}