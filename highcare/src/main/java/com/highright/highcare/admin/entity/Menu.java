package com.highright.highcare.admin.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name="TBL_MENU")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Menu {

    @Id
    private int menuCode;
    private String menuName;
    private String menuUrl;
    private String menuDesc;
    private String method;
    @Column(name = "GROUP_CODE")
    private String groupCode;
    private String id;
    private Date registDate;

    @Builder
    public Menu(int menuCode, String menuName, String menuUrl, String menuDesc, String method, String groupCode, String id, Date registDate) {
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
