package com.highright.highcare.bulletin.dto;

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

    private java.util.Date creationDate;

    private char deleteYn;

    private java.util.Date modifiedDate;

    private char allowComments;

    private int categoryCode;

    private int empNo;


}
