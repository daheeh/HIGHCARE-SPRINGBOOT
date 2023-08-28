package com.highright.highcare.auth.repository;

import com.highright.highcare.auth.entity.ADMAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<ADMAccount, String> {
    ADMAccount findByMemberId(String id);
}
