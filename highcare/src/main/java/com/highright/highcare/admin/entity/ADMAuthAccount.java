package com.highright.highcare.admin.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "adminAuthAccount")
@Table(name="TBL_AUTH_ACCOUNT")
@Getter
@Setter
public class ADMAuthAccount implements Serializable {

    private static final long serialVersionUID = 1L; // 추가


    @EmbeddedId
    private ADMAuthAccountId id;

    @Builder
    public ADMAuthAccount(ADMAuthAccountId id) {
        this.id = id;
    }
}
