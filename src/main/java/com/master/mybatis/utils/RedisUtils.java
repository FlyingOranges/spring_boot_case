package com.master.mybatis.utils;

import com.master.mybatis.CallInterface.RedisListInterface;
import com.master.mybatis.CallInterface.RedisObjectInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 创建一个 redis 工具类
 */
@Component
public class RedisUtils<T> {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 封装缓存记录方案(单记录)
     *
     * @param key
     * @param time
     * @param redisObjectInterface
     * @return
     */
    public Object remember(String key, long time, RedisObjectInterface redisObjectInterface) {

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        if (redisTemplate.hasKey(key)) {
            System.out.println("在缓存中找到了数据");
            return operations.get(key);
        }

        Object data = redisObjectInterface.getData();
        operations.set(key, data, time, TimeUnit.SECONDS);
        System.out.println("在缓存中没找到数据");

        return data;
    }

    /**
     * 封装缓存记录方案(多记录)
     *
     * @param key
     * @param time
     * @param redisListInterface
     * @return
     */
    public List<T> remember(String key, long time, RedisListInterface redisListInterface) {

        ValueOperations<String, List> operations = redisTemplate.opsForValue();

        if (redisTemplate.hasKey(key)) {
            logger.info("在缓存中找到了数据");
            return operations.get(key);
        }

        List<T> data = redisListInterface.getData();
        operations.set(key, data, time, TimeUnit.SECONDS);

        return data;
    }

}
