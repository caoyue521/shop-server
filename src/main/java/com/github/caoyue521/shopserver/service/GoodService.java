package com.github.caoyue521.shopserver.service;

import com.github.caoyue521.shopserver.dao.GoodDao;
import com.github.caoyue521.shopserver.dao.GoodDetailDao;
import com.github.caoyue521.shopserver.dao.SellerDao;
import com.github.caoyue521.shopserver.entity.Good;
import com.github.caoyue521.shopserver.entity.GoodDetail;
import com.github.caoyue521.shopserver.entity.Seller;
import com.github.caoyue521.shopserver.model.ApiResult;
import com.google.common.collect.Collections2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GoodService {
    @Resource
    GoodDao goodDao;
    @Resource
    SellerDao sellerDao;
    @Resource
    GoodDetailDao goodDetailDao;
    public void save(Good good){
        goodDao.save(good);
    }

    public ApiResult list() {
        List<Good> list = goodDao.findAll();
        ApiResult apiResult = ApiResult.ok("find good list ok");
        apiResult.setData(list);
        return apiResult;
    }

    public ApiResult getByid(Long id) {
        Good good = goodDao.findById(id).orElse(null);
        Seller seller = sellerDao.findById(good.getSeller_id()).orElse(null);
        good.setSeller(seller);
        GoodDetail goodDetail = goodDetailDao.findByGoodId(good.getId());
        good.setGoodDetail(goodDetail);
        List<Good> all = goodDao.findAll();
        Collections.shuffle(all);
        good.setRecommend(all.subList(0,6));
        ApiResult apiResult = ApiResult.ok("find good success");
        apiResult.setData(good);
        return apiResult;
    }

    public ApiResult getListByType(String type) {
        List<Good> list = goodDao.findAllByType(type);
        ApiResult apiResult = ApiResult.ok("find type good list ok");
        apiResult.setData(list);
        return apiResult;
    }

    public ApiResult add(Good good) {
        good= goodDao.save(good);
        ApiResult apiResult = ApiResult.ok("add good ok");
        apiResult.setData(good);
        return apiResult;
    }

    public ApiResult del(Long id) {
        goodDao.deleteById(id);
        ApiResult apiResult = ApiResult.ok("del good ok");
        return apiResult;
    }

    public ApiResult getListByType(String type, Integer page) {
        if(page==null){
            page =1;
        }
        Pageable pageable = PageRequest.of(page-1,4);
        Page<Good> goodPage =  this.goodDao.findByType(type,pageable);
        List<Good> list = goodPage.getContent();
        ApiResult apiResult = ApiResult.ok("add good ok");
        apiResult.setData(list);
        return apiResult;
    }
}
