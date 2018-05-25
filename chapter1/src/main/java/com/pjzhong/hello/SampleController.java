package com.pjzhong.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Value("server.name")
    private String name;

    @RequestMapping("/")
    String home() {
        return "Hello World! I am " + name;
    }
}
