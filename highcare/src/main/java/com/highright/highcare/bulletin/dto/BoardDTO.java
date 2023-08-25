package com.highright.highcare.bulletin.dto;

import com.highright.highcare.bulletin.entity.BulletinCategories;
import com.highright.highcare.bulletin.entity.BulletinEmployee;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardDTO {

    private int bulletinCode;

    private String title;

    private int views;

    private String content;

    private java.sql.Date creationDate;

    private char deleteYn;

    private java.sql.Date modifiedDate;

    private char allowComments;

    private BulletinCategories bulletinCategories;

    private BulletinEmployee BulletinEmployee;


}
