package com.highright.highcare.bulletin.entity;

import com.highright.highcare.bulletin.dto.BoardDTO;
import com.highright.highcare.bulletin.dto.BulletinEmployeeDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_COMMENT")
@SequenceGenerator(
    name = "COMMENT_CODE_SEQ_GENERATOR",
        sequenceName = "SEQ_COMMENT_CODE",
        initialValue = 1, allocationSize = 1
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Comment {
    @Id
    @Column(name = "COMMENT_CODE")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "COMMENT_CODE_SEQ_GENERATOR"
    )
    private int commentCode;
    @Column(name = "COMMENT_CONTENT")
    private String commentContent;
    @Column(name = "TOP_COMMENT_CODE")
    private Integer topCommentCode;
    @Column(name = "MODIFIED_DATE")
    private java.sql.Date modifiedDate;
    @Column(name = "DELETE_YN")
    private char deleteYn;
    @Column(name = "CREATION_DATE")
    private java.sql.Date creationDate;
    @ManyToOne
    @JoinColumn(name = "EMP_NO")
    private BulletinEmployee bulletinEmployee;
    @ManyToOne
    @JoinColumn(name = "BULLETIN_CODE")
    private Board board;
}
