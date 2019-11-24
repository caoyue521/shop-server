package com.github.caoyue521.shopserver.dao;

import com.github.caoyue521.shopserver.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SellerDao extends JpaSpecificationExecutor<Seller>, JpaRepository<Seller,Long> {

}
