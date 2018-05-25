package com.pjzhong;

import com.pjzhong.domain.User;
import com.pjzhong.domain.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@Transactional
@Import(Application.class)
public class ApplicationTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() {
        userMapper.insert("AAA", 20);
        User u = userMapper.findByName("AAA");
        Assert.assertEquals(20, u.getAge().intValue());

        u.setAge(30);
        userMapper.update(u);
        u = userMapper.findByName("AAA");
        Assert.assertEquals(30, u.getAge().intValue());

        u = new User("BBB", 30);
        userMapper.insertByUser(u);
        Assert.assertEquals(30, userMapper.findByName("BBB").getAge().intValue());

        Map<String, Object> map = new HashMap<>();
        map.put("name", "CCC");
        map.put("age", 40);
        userMapper.insertByMap(map);
        Assert.assertEquals(40, userMapper.findByName("CCC").getAge().intValue());

        for(User user : userMapper.findAll()) {
            Assert.assertNull(user.getId());
            Assert.assertNotNull(user.getName());
        }
    }
}
