package com.highright.highcare.admin.dto;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class AccessManagerDTO {

    private String id;
    private String isLock;
    private String isInActive;
    private String ixExpired;
    private String isWithDraw;
    private Timestamp registDate;
    private int loginFailCount;


    private String browser;

    private String device;

    @Builder

    public AccessManagerDTO(String id, String isLock, String isInActive, String ixExpired, String isWithDraw, String browser, String device) {
        this.id = id;
        this.isLock = isLock;
        this.isInActive = isInActive;
        this.ixExpired = ixExpired;
        this.isWithDraw = isWithDraw;
        this.browser = browser;
        this.device = device;
    }
}
