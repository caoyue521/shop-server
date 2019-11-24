package com.github.caoyue521.shopserver.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class GoodDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    long  goodId;

    String content;
}
