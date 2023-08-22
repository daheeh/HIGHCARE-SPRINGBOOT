package com.highright.highcare.bulletin.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_BULLETIN_CATEGORIES")
@SequenceGenerator(
        name = "BOARD_CATEGORY_SEQ_GENERATOR",
        sequenceName = "SEQ_TBL_CATEGORY_CODE",
        initialValue = 1, allocationSize = 1
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BulletinCategories {
    @Id
    @Column(name = "CATEGORY_CODE")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "BOARD_CATEGORY_SEQ_GENERATOR"
    )
    public int categoryCode;
    @Column(name = "NAME_BOARD")
    public String nameBoard;
}
