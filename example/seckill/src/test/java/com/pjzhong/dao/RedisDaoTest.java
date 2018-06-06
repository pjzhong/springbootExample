package com.pjzhong.dao;

import com.pjzhong.Application;
import com.pjzhong.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Import(Application.class)
public class RedisDaoTest {

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillMapper seckillMapper;

    @Test
    public void test() {
        int seckillId = 1000;
        Seckill seckill = redisDao.getSeckill(seckillId);
        if(seckill == null) {
            seckill = seckillMapper.queryById(seckillId);
            if(seckill != null) {
                String key = redisDao.putSeckill(seckill);
                System.out.println(key);
                Seckill sec = redisDao.getSeckill(seckillId);
                System.out.println(sec);
            }
        }
    }
}