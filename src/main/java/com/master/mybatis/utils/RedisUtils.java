package com.master.mybatis.utils;

import com.master.mybatis.CallInterface.RedisCallInterface;
import com.master.mybatis.entity.GirlsImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

public class RedisUtils {

    @Autowired
    private static RedisTemplate redisTemplate;

    public void RedisUtils() {
    }

    public static List<GirlsImages> remember(String key, long time, RedisCallInterface redisCallInterface) {

        ValueOperations<String, List> operations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(key)) {
            return operations.get(key);
        }

        return redisCallInterface.printName();
    }

}
