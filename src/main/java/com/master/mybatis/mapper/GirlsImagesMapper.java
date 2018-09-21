package com.master.mybatis.mapper;

import com.master.mybatis.entity.GirlsImages;
import com.master.mybatis.entity.GirlsImagesToContent;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface GirlsImagesMapper {

    GirlsImages findById(Integer id);

    List<GirlsImages> getGirls(Map<String, Object> params);

    Integer addGirls(GirlsImages girlsImages);

    Integer likeAction(Map<String, Object> map);

    Integer addGirlsList(List<GirlsImages> girlsImages);

    GirlsImagesToContent findJoinGirls(Integer id);

    List<GirlsImagesToContent> getJoinGirls(Map<String, Object> params);
}
