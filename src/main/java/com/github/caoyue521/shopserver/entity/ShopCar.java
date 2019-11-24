package com.github.caoyue521.shopserver.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class ShopCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
}
