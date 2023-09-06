package com.highright.highcare.admin.service;

import com.highright.highcare.admin.dto.RequestMemberDTO;
import com.highright.highcare.admin.dto.UpdateAccountDTO;

public interface AdminService {
    Object selectMember(int empNo);

    Object insertAccount(RequestMemberDTO requestMemberDTO);

    Object selectAccountList();


    Object deleteAccount(String id);

    Object updateAccount(String id, UpdateAccountDTO updateAccountDTO);
}
