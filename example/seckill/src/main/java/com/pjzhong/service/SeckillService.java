package com.pjzhong.service;

import com.pjzhong.dto.Exporter;
import com.pjzhong.dto.SeckillExecution;
import com.pjzhong.entity.Seckill;
import com.pjzhong.exception.RepeatKillException;
import com.pjzhong.exception.SeckillCloseException;
import com.pjzhong.exception.SeckillException;

import java.util.List;

/**
 * 业务接口:站在“使用者”角度设计接口
 * 三个方面：方法粒度，参数，返回类型(return 类型/异常)
 * */
public interface SeckillService {

    List<Seckill> getSeckills();

    Seckill getSeckill(int seckillId);

    /**
     * 秒杀开始输出秒杀接口地址
     * 否则输出系统时间和秒杀时间
     * */
    Exporter exportSeckillUrl(int seckillId);

    SeckillExecution executeSeckkill(int seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;
}
