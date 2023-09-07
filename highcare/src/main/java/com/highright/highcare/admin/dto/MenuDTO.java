package com.highright.highcare.admin.dto;


import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MenuDTO {

    private int menuCode;
    private String menuName;
    private String menuUrl;
    private String menuDesc;
    private String method;
    private String groupCode;
    private String id;
    private Date registDate;

    @Builder
    public MenuDTO(int menuCode, String menuName, String menuUrl, String menuDesc, String method, String groupCode, String id, Date registDate) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuUrl = menuUrl;
        this.menuDesc = menuDesc;
        this.method = method;
        this.groupCode = groupCode;
        this.id = id;
        this.registDate = registDate;
    }
}
