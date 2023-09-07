package com.highright.highcare.admin.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name="TBL_ACCESS_MANAGER")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AccessManager {

    @Id
    @Column(name="ID")
    private String id;

    @Column(name="REGIST_DATE")
    private Timestamp registDate;

    @Column(name="LOGIN_TOTAL_COUNT")
    private int loginTotalCount;

    @Column(name="LOGIN_FAIL_COUNT")
    private int loginFailCount;

    @Column(name = "IS_LOCK")
    @ColumnDefault("'Y'") // 기본값 'Y'로 설정
    private String isLock;

    @Column(name = "IS_INACTIVE")
    @ColumnDefault("'N'") // 기본값 'N'로 설정
    private String isInActive;

    @Column(name = "IS_EXPIRED")
    @ColumnDefault("'N'") // 기본값 'N'로 설정
    private String isExpired;

    @Column(name = "IS_WITHDRAW")
    @ColumnDefault("'N'") // 기본값 'N'로 설정
    private String isWithDraw;

    @Column(name="EXPIRED_DATE")
    private Date expiredDate;


    @Column(name="WITHDRAW_DATE")
    private Date withDrawDate;

    @Builder
    public AccessManager(String id, Timestamp registDate, int loginTotalCount, int loginFailCount, String isLock, String isInActive, String isExpired, Date expiredDate, String isWithDraw, Date withDrawDate) {
        this.id = id;
        this.registDate = registDate;
        this.loginTotalCount = loginTotalCount;
        this.loginFailCount = loginFailCount;
        this.isLock = isLock;
        this.isInActive = isInActive;
        this.isExpired = isExpired;
        this.expiredDate = expiredDate;
        this.isWithDraw = isWithDraw;
        this.withDrawDate = withDrawDate;
    }
}
