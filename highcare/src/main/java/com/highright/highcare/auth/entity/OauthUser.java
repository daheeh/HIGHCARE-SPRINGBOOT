package com.highright.highcare.auth.entity;

import com.highright.highcare.auth.dto.AuthDTO;
import com.highright.highcare.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class OauthUser extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public OauthUser(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public OauthUser(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
