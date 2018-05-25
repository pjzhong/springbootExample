package com.pjzhong.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
public class PostInfo {


    private String title;
    private String content;
}
