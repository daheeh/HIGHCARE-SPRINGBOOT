package com.highright.highcare.bulletin.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_BULLETIN_BOARD")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Board {
    @Id
    @Column(name = "BULLETIN_CODE")
    private int bulletinCode;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "VIEWS")
    private int views;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATION_DATE")
    private java.util.Date creationDate;

    @Column(name = "DELETE_YN")
    private char deleteYn;

    @Column(name = "MODIFIED_DATE")
    private java.util.Date modifiedDate;

    @Column(name = "ALLOW_COMMENTS")
    private char allowComments;
    @Column(name = "CATEGORY_CODE")
    private int categoryCode;

    @Column(name = "EMP_NO")
    private int empNo;


}
