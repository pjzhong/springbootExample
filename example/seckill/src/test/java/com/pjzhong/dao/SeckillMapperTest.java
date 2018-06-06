package com.pjzhong.dao;

import com.pjzhong.Application;
import com.pjzhong.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Import(Application.class)
@Transactional
public class SeckillMapperTest {

    @Autowired
    private SeckillMapper seckillMapper;

    @Test
    public void testQueryById() throws Exception {
        int id = 1000;
        Seckill seckill = seckillMapper.queryById(id);
        assertNotNull(seckill);
        assertNotNull(seckill.getName());
        assertNotNull(seckill.getStartTime());
        assertNotNull(seckill.getEndTime());
        assertNotNull(seckill.getCreateTime());
    }

    @Test
    public void testQueryAll() throws Exception {
        List<Seckill> seckills = seckillMapper.queryAll(0, 100);
        for(Seckill seckill : seckills) {
            assertNotNull(seckill);
            assertNotNull(seckill.getName());
            assertNotNull(seckill.getStartTime());
            assertNotNull(seckill.getEndTime());
            assertNotNull(seckill.getCreateTime());
        }
    }
}