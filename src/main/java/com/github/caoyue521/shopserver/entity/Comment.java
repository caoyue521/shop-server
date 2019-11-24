package com.github.caoyue521.shopserver.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.caoyue521.shopserver.serializer.DateToLongSerializer;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "good_id")
    long good_id;

    String message;
    @JsonSerialize(using = DateToLongSerializer.class)
    @UpdateTimestamp
    Date createDate;

    String uid;

    @Transient
    User user;



}
