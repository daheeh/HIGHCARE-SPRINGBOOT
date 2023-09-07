package com.highright.highcare.admin.repository;

import com.highright.highcare.admin.entity.ADMAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ADMAccountRepository extends JpaRepository<ADMAccount, String> {

    ADMAccount findByEmpNo(int empNo);

    List<ADMAccount> findAllByOrderByEmpNoAsc();

    ADMAccount findBymemberId(String id);
}
