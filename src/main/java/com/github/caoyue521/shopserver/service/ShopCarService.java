package com.github.caoyue521.shopserver.service;

import com.github.caoyue521.shopserver.dao.ShopCarDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ShopCarService {
    @Resource
    ShopCarDao shopCarDao;
}
