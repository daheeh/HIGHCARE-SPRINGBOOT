package com.highright.highcare.auth.repository;

import com.highright.highcare.auth.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByMemberId(String id);
}
