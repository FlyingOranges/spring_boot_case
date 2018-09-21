package com.master.mybatis.api;

import com.master.mybatis.entity.GirlsImages;
import com.master.mybatis.entity.GirlsImagesToContent;
import com.master.mybatis.service.GirlsImagesService;
import com.master.mybatis.utils.HttpUtils;
import com.master.mybatis.utils.Query;
import com.master.mybatis.utils.StringRandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.master.mybatis.domain.packages.ApiResponse;

import java.util.List;
import java.util.Map;

@RestController
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private GirlsImagesService girlsImagesService;

    @PostMapping(value = "")
    public ApiResponse index(@RequestParam Map<String, Object> param) {
        Query query = new Query(param);

        List<GirlsImages> list = girlsImagesService.getList(query);

        return HttpUtils.apiSuccess(null, list);
    }

    @GetMapping(value = "/girls")
    public ApiResponse girls(@RequestParam("id") Integer id) {
        GirlsImages girlsImages = girlsImagesService.findGirls(id);

        return HttpUtils.apiSuccess(null, girlsImages);
    }

    @GetMapping(value = "/create")
    public ApiResponse create() {

        GirlsImages girlsImages = new GirlsImages();

        girlsImages.setTitle(StringRandomUtils.getRandomString(5));
        girlsImages.setSrc("http://mm.chinasareview.com/wp-content/uploads/2017a/07/17/11.jpg");
        girlsImages.setLike(1);

        Integer result = girlsImagesService.addGirls(girlsImages);

        if (result < 1) {
            return HttpUtils.apiError("新增失败");
        }

        return HttpUtils.apiSuccess(null, girlsImages);
    }

    @PostMapping(value = "/like")
    public ApiResponse update(@RequestParam("id") Integer id) {

        Integer result = girlsImagesService.likeAction(id);

        if (result > 0) {
            return HttpUtils.apiSuccess("操作成功");
        }

        return HttpUtils.apiError("操作失败");
    }

    @GetMapping(value = "/join")
    public ApiResponse join() {
        GirlsImagesToContent data = girlsImagesService.findJoinGirls(20);

        return HttpUtils.apiSuccess(null, data);
    }

    @GetMapping(value = "/joinlist")
    public ApiResponse joinList(@RequestParam Map<String, Object> params) {

        Query query = new Query(params);

        List<GirlsImagesToContent> data = girlsImagesService.getJoinGirls(query);
        return HttpUtils.apiSuccess(null, data);
    }

    @GetMapping(value = "/redis")
    public ApiResponse redis() {
        return HttpUtils.apiSuccess(null, girlsImagesService.getRedisGirls(2));
    }

    @GetMapping(value = "/redis/list")
    public ApiResponse redisSet(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<GirlsImages> data = girlsImagesService.getRedisGirlsList(query);

        return HttpUtils.apiSuccess(null, data);
    }

    @GetMapping(value = "/redis/del")
    public ApiResponse redisDel() {
        return HttpUtils.apiSuccess(null, girlsImagesService.delRedisGirls(2));
    }

    @GetMapping(value = "/redis/utils")
    public ApiResponse redisUtils() {
        GirlsImages data = girlsImagesService.GetRedisGirlsUtils(2);

        return HttpUtils.apiSuccess(null, data);
    }

    @GetMapping(value = "/redis/list/utils")
    public ApiResponse redisList(@RequestParam Map<String, Object> params) {

        Query query = new Query(params);

        List<GirlsImages> data = girlsImagesService.GetRedisGirlsListUtils(query);

        return HttpUtils.apiSuccess(null, data);
    }
}
