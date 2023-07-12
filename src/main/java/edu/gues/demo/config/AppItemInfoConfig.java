package edu.gues.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author mxuexxmy
 */
@Configuration
@Slf4j
public class AppItemInfoConfig {

    public static  String appInfo;

    @Value("${app-info:DeploymentLocationConstant.GY}")
    public void setAppInfo(String appInfo) {
        log.info("appInfo: " + appInfo);
        AppItemInfoConfig.appInfo = appInfo;
    }

}
