package com.highright.highcare.auth.service;

import com.highright.highcare.auth.dto.LoginMemberDTO;

public interface AuthService {

    Object login(LoginMemberDTO loginMemberDTO);
}
