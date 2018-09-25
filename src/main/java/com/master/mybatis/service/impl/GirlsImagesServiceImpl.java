package com.master.mybatis.service.impl;

import com.master.mybatis.CallInterface.RedisListInterface;
import com.master.mybatis.CallInterface.RedisObjectInterface;
import com.master.mybatis.entity.GirlsImages;
import com.master.mybatis.entity.GirlsImagesToContent;
import com.master.mybatis.mapper.GirlsImagesMapper;
import com.master.mybatis.service.GirlsImagesService;
import com.master.mybatis.utils.RedisUtils;
import com.master.mybatis.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service(value = "GirlsImagesService")
public class GirlsImagesServiceImpl implements GirlsImagesService {

    private static final Logger logger = LoggerFactory.getLogger(GirlsImagesServiceImpl.class);

    private static final Integer NORMAL = 1;
    private static final Integer DELETE = 1;

    @Autowired
    private GirlsImagesMapper girlsImagesMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public GirlsImages findGirls(Integer id) {
        System.out.println("到这里来了");
        return girlsImagesMapper.findById(id);
    }

    @Override
    public List<GirlsImages> getList(Map<String, Object> params) {
        return girlsImagesMapper.getGirls(params);
    }

    @Override
    @Transactional
    public Integer addGirls(GirlsImages girlsImages) {

        girlsImages.setCreatedAt(TimeUtils.getTimestamp());
        girlsImages.setUpdatedAt(TimeUtils.getTimestamp());

        girlsImages.setStatus(NORMAL);

        return girlsImagesMapper.addGirls(girlsImages);
    }

    @Override
    @Transactional
    public Integer likeAction(Integer id) {
        Map<String, Object> map = new HashMap<>();

        map.put("id", id);
        map.put("updatedAt", TimeUtils.getTimestamp());

        return girlsImagesMapper.likeAction(map);
    }

    @Override
    public GirlsImagesToContent findJoinGirls(Integer id) {
        return girlsImagesMapper.findJoinGirls(id);
    }

    @Override
    public List<GirlsImagesToContent> getJoinGirls(Map<String, Object> params) {
        return girlsImagesMapper.getJoinGirls(params);
    }

    @Override
    public GirlsImages getRedisGirls(Integer id) {
        // 从缓存中获取数据信息
        String key = "GIRLS_" + id;

        ValueOperations<String, GirlsImages> operations = redisTemplate.opsForValue();

        // 缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            GirlsImages girlsImages = operations.get(key);

            return girlsImages;
        }

        GirlsImages girlsImages = girlsImagesMapper.findById(id);

        // 插入缓存
        operations.set(key, girlsImages, 600, TimeUnit.SECONDS);

        return girlsImages;
    }

    @Override
    public List<GirlsImages> getRedisGirlsList(Map<String, Object> params) {
        // 从缓存中获取数据信息
        String key = "GIRLS_LIST_OFFSET_" + params.get("offset")
                + "_PAGE_" + params.get("page") + "_LIMIT_" + params.get("limit");

        ValueOperations<String, List> operations = redisTemplate.opsForValue();

        if (redisTemplate.hasKey(key)) {
            List<GirlsImages> girlsImages = operations.get(key);
            System.out.println("进入了缓存redis");
            return girlsImages;
        }

        List<GirlsImages> girlsImages = girlsImagesMapper.getGirls(params);

        operations.set(key, girlsImages, 600, TimeUnit.SECONDS);
        System.out.println("没进入redis");

        return girlsImages;
    }

    @Override
    public Integer delRedisGirls(Integer id) {
        String key = "GIRLS_" + id;

        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
            logger.info(key + " 缓存被删除了");
        }
        return 1;
    }

    @Override
    public GirlsImages GetRedisGirlsUtils(Integer id) {

        /*
        用匿名函数操作
        GirlsImages data = (GirlsImages) redisUtils.remember("key_" + id, 10, (new RedisObjectInterface() {
            @Override
            public Object getData() {
                return girlsImagesMapper.findById(id);
            }
        }));
        */

        // 用lambda表达式
        return (GirlsImages) redisUtils.remember("key_" + id, 10, () -> girlsImagesMapper.findById(id));
    }

    @Override
    public List<GirlsImages> GetRedisGirlsListUtils(Map<String, Object> params) {
        String key = "GIRLS_LIST_OFFSET_" + params.get("offset")
                + "_PAGE_" + params.get("page") + "_LIMIT_" + params.get("limit");

        return redisUtils.remember(key, 20, () -> girlsImagesMapper.getGirls(params));
    }
}
