package com.github.caoyue521.shopserver.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String uid;

    String name;

    String mobile;

    String password;

    String remark;

    String photo;

}

