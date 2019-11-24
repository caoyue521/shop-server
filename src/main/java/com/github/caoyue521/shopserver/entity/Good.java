package com.github.caoyue521.shopserver.entity;


import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;
@ApiModel("商品信息")
@Data
@Entity
@TypeDef(name ="json",typeClass = JsonStringType.class)
public class Good {
    @ApiModelProperty(value = "主键", example = "0", position = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ApiModelProperty(value = "商品名称", example = "商品名称", position = 2)
    String name;
    @ApiModelProperty(value = "价格", example = "100", position = 3)
    long price;
    @ApiModelProperty(value = "主键", example = "1", position = 4)
    String summary;
    @ApiModelProperty(value = "商品类型", example = "pop", position = 5)
    String type;
    @ApiModelProperty(value = "商品参数", example = "1", position = 6)
    String sku;
    @ApiModelProperty(value = "商品展示图片", example = "1", position = 7)
    String pic;
    @ApiModelProperty(value = "卖家id", example = "1", position = 8)
    @Column(name = "seller_id")
    long seller_id;
    @ApiModelProperty(value = "商品轮播图",  position = 9)
    @Type(type="json")
    @Column(columnDefinition = "json")
    List<String> banners;
    @ApiModelProperty(readOnly = true,required = false,value = "评论列表",  position = 10)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "good_id")
    List<Comment> comments;
    @ApiModelProperty(readOnly = true,required = false,value = "商品详情" ,notes = "只供展示使用", position = 11,hidden = true)
    @Transient
    GoodDetail goodDetail;
    @ApiModelProperty(readOnly = true,required = false,value = "商家信息",notes = "只供展示使用", position = 12,hidden = true)
    @Transient
    Seller seller;
    @ApiModelProperty(readOnly = true,required = false,value = "推荐商品信息",notes = "只供展示使用", position = 13,hidden = true)
    @Transient
    List<Good> recommend;













}
