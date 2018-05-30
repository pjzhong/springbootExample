package com.pjzhong;

import com.pjzhong.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Import(Application.class)
public class ApplicationTest {

    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    @Test
    public void test() {
        template.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", template.opsForValue().get("aaa"));

        //Using jdk Serializer
        User user = new User("你", 20);
        redisTemplate.opsForValue().set(user.getName(), user);

        user = new User("我", 30);
        redisTemplate.opsForValue().set(user.getName(), user);

        user = new User("Ta", 40);
        redisTemplate.opsForValue().set(user.getName(), user);

        Assert.assertEquals(20L, redisTemplate.opsForValue().get("你").getAge().longValue());
        Assert.assertEquals(30L, redisTemplate.opsForValue().get("我").getAge().longValue());
        Assert.assertEquals(40L, redisTemplate.opsForValue().get("Ta").getAge().longValue());

        Assert.assertEquals("你", redisTemplate.opsForValue().get("你").getName());
        Assert.assertEquals("我", redisTemplate.opsForValue().get("我").getName());
        Assert.assertEquals("Ta", redisTemplate.opsForValue().get("Ta").getName());
    }
}
