package com.highright.highcare.auth.service;

import com.highright.highcare.auth.dto.LoginMemberDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    Object selectLogin(LoginMemberDTO loginMemberDTO, HttpServletResponse response);

    Object reIssueToken(HttpServletRequest request);

}
