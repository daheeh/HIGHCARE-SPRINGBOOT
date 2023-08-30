package com.highright.highcare.admin.repository;

import com.highright.highcare.admin.entity.ADMAuthAccount;
import com.highright.highcare.admin.entity.ADMAuthAccountId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ADMAuthAccountRepository extends JpaRepository<ADMAuthAccount, ADMAuthAccountId> {

}
