package com.pjzhong.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 暴露秒杀地址DTO
 * */
@Getter
@Setter
@ToString
public class Exporter {
    //Is expose seckill(start or not)
    private boolean exposed;
    private String md5;
    private long seckillId;
    private long now;//millis
    private long start;
    private long end;

    public Exporter(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exporter(boolean exposed, int seckillId, long now, long start, long end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exporter(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }
}
