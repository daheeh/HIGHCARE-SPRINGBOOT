package com.highright.highcare.admin.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    @Column(name="IS_LOCK")
    private String isLock;
    @Column(name="IS_INACTIVE")
    private String isInActive;
    @Column(name="IS_EXPIRED")
    private String ixExpired;
    @Column(name="IS_WITHDRAW")
    private String isWithDraw;


    @Builder
    public AccessManager(String id, String isLock, String isInActive, String ixExpired, String isWithDraw) {
        this.id = id;
        this.isLock = isLock;
        this.isInActive = isInActive;
        this.ixExpired = ixExpired;
        this.isWithDraw = isWithDraw;
    }
}
