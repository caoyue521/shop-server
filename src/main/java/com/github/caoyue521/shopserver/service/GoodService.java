package com.github.caoyue521.shopserver.service;

import com.github.caoyue521.shopserver.dao.GoodDao;
import com.github.caoyue521.shopserver.dao.GoodDetailDao;
import com.github.caoyue521.shopserver.dao.SellerDao;
import com.github.caoyue521.shopserver.entity.Good;
import com.github.caoyue521.shopserver.entity.GoodDetail;
import com.github.caoyue521.shopserver.entity.Seller;
import com.github.caoyue521.shopserver.model.ApiResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
