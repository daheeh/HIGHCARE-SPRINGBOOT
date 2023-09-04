package com.highright.highcare.admin.dto;

import lombok.*;

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

    @Builder
    public AccessManagerDTO(String id, String isLock, String isInActive, String ixExpired, String isWithDraw) {
        this.id = id;
        this.isLock = isLock;
        this.isInActive = isInActive;
        this.ixExpired = ixExpired;
        this.isWithDraw = isWithDraw;
    }



}
