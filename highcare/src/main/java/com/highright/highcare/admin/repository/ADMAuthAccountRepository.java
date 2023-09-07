package com.highright.highcare.admin.repository;

import com.highright.highcare.admin.entity.ADMAuthAccount;
import com.highright.highcare.admin.entity.ADMAuthAccountId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ADMAuthAccountRepository extends JpaRepository<ADMAuthAccount, ADMAuthAccountId> {


//    List<ADMAuthAccount> findByADMAuthAccountId_Id(String id);

    List<ADMAuthAccount> findById_Id(String id);
}
