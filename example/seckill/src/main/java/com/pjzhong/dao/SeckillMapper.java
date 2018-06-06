package com.pjzhong.dao;

import com.pjzhong.entity.Seckill;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface SeckillMapper {

    @Update("UPDATE seckill set number = number - 1 where seckill_id=#{seckillId} and start_time <= #{killTime} and #{killTime} <= end_time and 0 < number")
    int reduceNumber(@Param("seckillId") int seckillId, @Param("killTime") Date killTime);

    @Select("SELECT seckill_id, name, number, start_Time, end_time, create_time from seckill where seckill_id=#{seckillId}")
    Seckill queryById(@Param("seckillId") int seckillId);

    @Select("SELECT seckill_id, name, number, start_Time, end_time, create_time from seckill order by create_time desc limit #{offset}, #{limit}")
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
