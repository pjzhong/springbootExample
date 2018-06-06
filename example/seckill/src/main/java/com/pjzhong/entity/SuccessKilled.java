package com.pjzhong.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class SuccessKilled {

    private int seckillId;
    private long userPhone;
    private short state;
    private Date createTime;

    //multi to one
    private Seckill seckill;
}
