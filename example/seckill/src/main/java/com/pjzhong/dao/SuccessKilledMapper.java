package com.pjzhong.dao;

import com.pjzhong.entity.SuccessKilled;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SuccessKilledMapper {

    @Insert("INSERT IGNORE INTO success_killed(seckill_id, user_phone, state) values(#{seckillId}, #{userPhone}, 1)")
    int insertSuccessKilled(@Param("seckillId") int secckillId, @Param("userPhone") long userPhone);

    SuccessKilled queryByIdWithSeckill(@Param("seckillId") int seckillId, @Param("userPhone") long userPhone);
}
