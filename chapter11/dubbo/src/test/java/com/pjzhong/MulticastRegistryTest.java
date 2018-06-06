package com.pjzhong;

import com.pjzhong.dubbo.DemoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MulticastRegistryTest {

    ClassPathXmlApplicationContext remoteContext;

    @Before
    public void initRemote() {
        remoteContext = new ClassPathXmlApplicationContext("service_provider.xml");
        remoteContext.start();
    }

    @Test
    public void test() {
        ClassPathXmlApplicationContext localContext = new ClassPathXmlApplicationContext("client_consumer.xml");
        localContext.start();

        DemoService demo = localContext.getBean(DemoService.class);
        String hiMessage= demo.sayHello("World!");
        System.out.println(hiMessage);
        Assert.assertEquals("Hello World!", hiMessage);
    }


}
