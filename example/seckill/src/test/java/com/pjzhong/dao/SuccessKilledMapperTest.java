package com.pjzhong.dao;

import com.pjzhong.Application;
import com.pjzhong.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Import(Application.class)
@Transactional
public class SuccessKilledMapperTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SuccessKilledMapper successKilledMapper;

    @Test
    public void testInsertSuccessKilled() throws Exception {
        int id = 1000;
        long phone = 17322000321L;
        successKilledMapper.insertSuccessKilled(id, phone);

        SuccessKilled successKilled = successKilledMapper.queryByIdWithSeckill(id, phone);
        assertNotNull(successKilled);
        assertEquals(id, successKilled.getSeckillId());
        assertEquals(phone, successKilled.getUserPhone());
        logger.info("successKilled:{}", successKilled);
    }
}