package com.github.caoyue521.shopserver.dao;

import com.github.caoyue521.shopserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDao extends JpaSpecificationExecutor<User>, JpaRepository<User,Long> {

}
