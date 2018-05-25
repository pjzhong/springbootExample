package com.pjzhong.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
public class FooProperties {

    private String foo;

    private String databasePlatform;
}
