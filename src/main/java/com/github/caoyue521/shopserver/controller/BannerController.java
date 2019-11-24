package com.github.caoyue521.shopserver.controller;

import com.github.caoyue521.shopserver.dao.GoodDao;
import com.github.caoyue521.shopserver.entity.Good;
import com.github.caoyue521.shopserver.model.ApiResult;
import com.github.caoyue521.shopserver.service.GoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/banner")
@Slf4j
public class BannerController {
    @Resource
    GoodDao goodDao;
    @GetMapping("/home")
    public ApiResult getBanners(){
        List<Map> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            Good good = goodDao.findAll().get(i);
            Map<String,Object> bannerMap = new HashMap<>(5);
            bannerMap.put("goodId",good.getId());
            bannerMap.put("bannerPic",good.getPic());
            list.add(bannerMap);
        }
        ApiResult apiResult = ApiResult.ok("get success");
        apiResult.setData(list);
        return  apiResult;
    }
}
