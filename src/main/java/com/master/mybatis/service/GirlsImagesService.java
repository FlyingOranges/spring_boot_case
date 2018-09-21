package com.master.mybatis.service;

import com.master.mybatis.entity.GirlsImages;
import com.master.mybatis.entity.GirlsImagesToContent;

import java.util.List;
import java.util.Map;

public interface GirlsImagesService {

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    GirlsImages findGirls(Integer id);

    /**
     * 获取列表数据
     *
     * @return
     */
    List<GirlsImages> getList(Map<String, Object> params);

    /**
     * 新增数据
     *
     * @param girlsImages
     * @return
     */
    Integer addGirls(GirlsImages girlsImages);

    /**
     * 喜欢+1
     *
     * @param id
     * @return
     */
    Integer likeAction(Integer id);

    /**
     * 联表查询数据
     *
     * @param id
     * @return
     */
    GirlsImagesToContent findJoinGirls(Integer id);

    /**
     * 获取连表数据
     *
     * @param params
     * @return
     */
    List<GirlsImagesToContent> getJoinGirls(Map<String, Object> params);

    /**
     * 获取缓存中的数据
     *
     * @param id
     * @return
     */
    GirlsImages getRedisGirls(Integer id);

    /**
     * 获取缓存中的数据列表
     *
     * @param params
     * @return
     */
    List<GirlsImages> getRedisGirlsList(Map<String, Object> params);

    /**
     * 删除数据的redis信息
     *
     * @param id
     * @return
     */
    Integer delRedisGirls(Integer id);

    //TODO 工具封装redis查找
    List<GirlsImages> getRedisUtilsList(Map<String, Object> params);
}
