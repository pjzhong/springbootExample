package com.pjzhong.dao;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.pjzhong.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    @Value("${jedis.password}")
    private String password;

    @Autowired
    public RedisDao(@Value("${jedis.host}") String host, @Value("${jedis.port}") int port) {
        jedisPool = new JedisPool(host, port);
    }

    public Seckill getSeckill(int seckillId) {
        try(Jedis jedis = getJedis()){
            String key = "seckill:" + seckillId;
            byte[] bytes = jedis.get(key.getBytes());
            if(bytes != null) {
                Seckill seckill = schema.newMessage();
                ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                return seckill;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String putSeckill(Seckill seckill) {
        try(Jedis jedis = getJedis()){
            String key = "seckill:" + seckill.getSeckillId();
            byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            jedis.setex(key.getBytes(), 60 * 60, bytes);
            return key;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        if(password != null) { jedis.auth(password);}
        return jedis;
    }
}
