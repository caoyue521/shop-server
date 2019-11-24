package com.github.caoyue521.shopserver.dao;

import com.github.caoyue521.shopserver.entity.ShopCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShopCarDao extends JpaRepository<ShopCar,Long>, JpaSpecificationExecutor<ShopCar> {
}
