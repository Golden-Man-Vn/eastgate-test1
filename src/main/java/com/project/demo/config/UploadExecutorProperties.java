package com.project.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "upload.executor")
public class UploadExecutorProperties {
    private int corePoolSize;
    private int maxPoolSize;
    private int queueCapacity;
}

