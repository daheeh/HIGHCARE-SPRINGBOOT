package com.highright.highcare.mypage.entity;

import lombok.*;
import org.springframework.context.annotation.Profile;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="TBL_MY_PROFILE_FILE")
@Getter
@Setter
@SequenceGenerator(
        name = "MYFILE_SEQ_NO",       // 엔티티 안에서 저장되는 이름
        sequenceName = "FILE_SEQ_NO",       // DB에 저장되는 이름
        initialValue = 1, allocationSize = 1
)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyProfileFile {

    @Id
    @Column (name = "PHOTO_FILE_CODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYFILE_SEQ_NO")
    private int photoCode;

    @Column(name="PROFILE_CODE")
    private int code;

    @Column(name = "ORIGINAL_FILE_NAME")
    private String name;

    @Column(name = "CHANGED_FILE_NAME")
    private String chName;

    @Column(name = "CREATION_DATE" ,nullable = true)
    private Date date;



}
