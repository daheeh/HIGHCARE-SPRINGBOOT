package com.highright.highcare.admin.service;

import com.highright.highcare.admin.dto.MenuDTO;
import com.highright.highcare.admin.dto.MenuManagerDTO;
import com.highright.highcare.admin.dto.RequestMemberDTO;
import com.highright.highcare.admin.dto.UpdateAccountDTO;

import java.util.List;

public interface AdminService {
    Object selectMember(int empNo);

    Object insertAccount(RequestMemberDTO requestMemberDTO);

    Object selectAccountList();


    Object deleteAccount(String id);

    Object updateAccount(String id, UpdateAccountDTO updateAccountDTO);

    Object selectJobList();

    Object selectDepartmentsList();

    Object selectMenuGroupList();

    Object insertMenuManagers(MenuManagerDTO menuManagerDTO);

    Object deleteMenuManagers(String[] ids);

    Object selectAccessLog();

    Object selectSearchMemberLog(String keyword);
}
