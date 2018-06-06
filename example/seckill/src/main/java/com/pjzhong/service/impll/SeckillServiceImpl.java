package com.pjzhong.service.impll;

import com.pjzhong.dao.RedisDao;
import com.pjzhong.dao.SeckillMapper;
import com.pjzhong.dao.SuccessKilledMapper;
import com.pjzhong.dto.Exporter;
import com.pjzhong.dto.SeckillExecution;
import com.pjzhong.entity.Seckill;
import com.pjzhong.entity.SuccessKilled;
import com.pjzhong.enums.SeckillStatEnum;
import com.pjzhong.exception.RepeatKillException;
import com.pjzhong.exception.SeckillCloseException;
import com.pjzhong.exception.SeckillException;
import com.pjzhong.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService  {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String slat = "asldfjl;kajsdflkSDFiyr0oipquywe057r9-q827345";

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private SuccessKilledMapper successKilledMapper;

    @Autowired
    private RedisDao redisDao;

    @Override
    public List<Seckill> getSeckills() {
        return seckillMapper.queryAll(0, 4);
    }

    @Override
    public Seckill getSeckill(int seckillId) {
        return seckillMapper.queryById(seckillId);
    }

    @Override
    public Exporter exportSeckillUrl(int seckillId) {
        Seckill seckil = redisDao.getSeckill(seckillId);
        if(seckil == null) {
            seckil = seckillMapper.queryById(seckillId);
            if(seckil == null) {
                return new Exporter(false, seckillId);
            } else {
                redisDao.putSeckill(seckil);
            }
        }

        Date startTime = seckil.getStartTime(), endTime = seckil.getEndTime();

        Date nowTime = new Date();
        if(nowTime.getTime() < startTime.getTime() || endTime.getTime() < nowTime.getTime()) {
            return new Exporter(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        return new Exporter(true, getMD5(seckillId), seckillId);
    }

    @Override
    @Transactional
    public SeckillExecution executeSeckkill(int seckillId, long userPhone, String md5) throws SeckillException {
        if(md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill date rewrite");
        }

        //逻辑：减库存 + 记录购买行为
        try {
            Date nowTime = new Date();
            int updateCount = seckillMapper.reduceNumber(seckillId, nowTime);
            if(updateCount <= 0) {
                throw new SeckillCloseException("seckill close");
            } else {
                int insertCount = successKilledMapper.insertSuccessKilled(seckillId, userPhone);
                if(insertCount <= 0) {
                    throw new RepeatKillException("Repeat seckill");
                } else {
                    SuccessKilled successKilled = successKilledMapper.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillException sec) {
            throw sec;
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }

    private String getMD5(int seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());

        return md5;
    }
}
