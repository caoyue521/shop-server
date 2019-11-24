package com.github.caoyue521.shopserver.service;

import com.github.caoyue521.shopserver.dao.SellerDao;
import com.github.caoyue521.shopserver.entity.Seller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SellerService {
    @Resource
    SellerDao sellerDao;
    public void save(Seller seller){
        sellerDao.save(seller);
    }
}
