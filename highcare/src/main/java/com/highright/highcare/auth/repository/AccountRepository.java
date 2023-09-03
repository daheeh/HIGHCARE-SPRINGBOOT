package com.highright.highcare.auth.repository;

import com.highright.highcare.auth.entity.AUTHAccount;
import com.highright.highcare.auth.entity.AUTHEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AUTHAccount, String> {
    AUTHAccount findByMemberId(String id);


    Optional<AUTHAccount> findByEmployee_EmailAndEmployee_Name(String email, String name);

    AUTHAccount findByEmployee_Email(String email);
}
