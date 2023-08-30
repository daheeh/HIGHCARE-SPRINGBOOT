package com.highright.highcare.auth.dto;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class AccountDTO {
    
    private String memberId;
    private int empNo;
    private String password;
    private String isTempPwd;
    private Date pwdExpiredDate;

}
