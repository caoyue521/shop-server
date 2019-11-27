package com.github.caoyue521.shopserver.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@ApiModel("用户信息")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", example = "0", position = 1)
    long id;
    @ApiModelProperty(value = "登录名", example = "name", position = 2)
    String uid;
    @ApiModelProperty(value = "姓名", example = "姓名", position = 3)
    String name;
    @ApiModelProperty(value = "手机号", example = "13111111111", position = 4)
    String mobile;
    @ApiModelProperty(value = "密码", example = "123456", position = 5)
    String password;
    @ApiModelProperty(value = "备注", example = "备注", position = 6)
    String remark;
    @ApiModelProperty(value = "头像", example = "http://b-ssl.duitang.com/uploads/item/201208/30/20120830173930_PBfJE.jpeg", position = 7)
    String photo;

}

