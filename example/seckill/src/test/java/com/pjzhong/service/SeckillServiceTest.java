package com.pjzhong.service;

import com.pjzhong.Application;
import com.pjzhong.dto.Exporter;
import com.pjzhong.dto.SeckillExecution;
import com.pjzhong.entity.Seckill;
import com.pjzhong.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Import(Application.class)
@Transactional
public class SeckillServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckills() throws Exception {
        List<Seckill> list = seckillService.getSeckills();
        logger.info("list{}", list);
    }

    @Test
    public void testGetSeckill() throws Exception {
        int id = 1000;
        Seckill seckill = seckillService.getSeckill(id);
        assertNotNull(seckill);
        assertEquals(id, seckill.getSeckillId());
    }

    @Test
    public void testSeckillLogic() {
        int id = 1003;
        Exporter exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);

        if(exposer.isExposed()) {
            long phone = 123456789L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution execution = seckillService.executeSeckkill(id, phone, md5);
                logger.info("SeckillExecution={}", execution);
            } catch (SeckillException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.warn("exposer={}", exposer);
        }

    }
}