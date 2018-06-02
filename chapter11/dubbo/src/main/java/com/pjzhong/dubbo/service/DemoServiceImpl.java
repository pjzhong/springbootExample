package com.pjzhong.dubbo.service;

import com.pjzhong.dubbo.DemoService;


public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
