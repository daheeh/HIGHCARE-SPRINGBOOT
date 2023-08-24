package com.highright.highcare.admin.service;

import com.highright.highcare.admin.dto.RequestMemberDTO;

public interface AdminService {
    Object selectMember(int empNo);

    Object insertMember(RequestMemberDTO requestMemberDTO);
}
