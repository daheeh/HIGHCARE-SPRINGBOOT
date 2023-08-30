package com.highright.highcare.mypage.dto;

import com.highright.highcare.mypage.entity.MyBscd;
import com.highright.highcare.mypage.entity.MyEmployee;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyBscdDTO {

    private int code;
    private String name;
    private String phone;
    private String email;
    private String company;
    private String address;
    private String content;
    private String empNo;

    private MyEmployee myEmp;


}
