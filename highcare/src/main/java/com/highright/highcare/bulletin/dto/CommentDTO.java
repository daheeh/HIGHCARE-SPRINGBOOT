package com.highright.highcare.bulletin.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentDTO {
    private int commentCode;
    private String commentContent;
    private Integer topCommentCode;
    private java.sql.Date modifiedDate;
    private char deleteYn;
    private java.sql.Date creationDate;
    private BulletinEmployeeDTO bulletinEmployee;
    private BoardDTO board;

}
