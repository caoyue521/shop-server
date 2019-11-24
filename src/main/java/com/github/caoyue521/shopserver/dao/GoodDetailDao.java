package com.github.caoyue521.shopserver.dao;

import com.github.caoyue521.shopserver.entity.GoodDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodDetailDao extends JpaSpecificationExecutor<GoodDetail>, JpaRepository<GoodDetail,Long> {
    GoodDetail findByGoodId(long id);
}
