package com.highright.highcare.bulletin.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_BULLETIN_BOARD")
@SequenceGenerator(
        name = "BOARD_CODE_SEQ_GENERATOR",
        sequenceName = "SEQ_BOARD_CODE",
        initialValue = 1, allocationSize = 1
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Board {
    @Id
    @Column(name = "BULLETIN_CODE")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "BOARD_CODE_SEQ_GENERATOR"
    )
    private int bulletinCode;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "VIEWS")
    private int views;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATION_DATE")
    private java.sql.Date creationDate;

    @Column(name = "DELETE_YN")
    private char deleteYn;

    @Column(name = "MODIFIED_DATE")
    private java.sql.Date modifiedDate;

    @Column(name = "ALLOW_COMMENTS")
    private char allowComments;
    @ManyToOne
    @JoinColumn(name = "CATEGORY_CODE")
    private BulletinCategories bulletinCategories;
    @ManyToOne
    @JoinColumn(name = "EMP_NO")
    private BulletinEmployee bulletinEmployee;

}
