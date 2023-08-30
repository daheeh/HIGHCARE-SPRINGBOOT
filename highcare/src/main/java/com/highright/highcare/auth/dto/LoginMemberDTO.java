package com.highright.highcare.auth.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginMemberDTO implements UserDetails {

    private int empNo;
    private String id;
    private String Name;
    private String password;

    private String deptName;
    private String jobName;

    private List<AuthAccountDTO> roleList;

    private Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return getId();
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


    @Builder
    public LoginMemberDTO(int empNo, String id, String name, String password,
                          String deptName, String jobName,
                          List<AuthAccountDTO> roleList) {
        this.empNo = empNo;
        this.id = id;
        Name = name;
        this.password = password;
        this.deptName = deptName;
        this.jobName = jobName;
        this.roleList = roleList;
    }
}
