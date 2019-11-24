package com.github.caoyue521.shopserver.dao;

import com.github.caoyue521.shopserver.entity.Good;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface GoodDao extends JpaSpecificationExecutor<Good>, JpaRepository<Good,Long> {

    List<Good> findAllByType(String type);
    Page<Good> findByType(String type, Pageable pageable);
}
