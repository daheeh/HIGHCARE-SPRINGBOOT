package com.highright.highcare.admin.repository;

import com.highright.highcare.admin.entity.ADMAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ADMAccountRepository extends JpaRepository<ADMAccount, String> {

    ADMAccount findByEmpNo(int empNo);

    List<ADMAccount> findAllByOrderByEmpNoAsc();

    ADMAccount findBymemberId(String id);


    Page<ADMAccount> findAllByOrderByAccessManager_RegistDateDesc(Pageable pageable);




    //    List<ADMAccount> findByAccessManager_RegistDateBetweenOrderByAccessManager_RegistDateDesc(LocalDateTime start, LocalDateTime end);
    Page<ADMAccount> findByAccessManager_RegistDateBetweenOrderByAccessManager_RegistDateDesc(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query(value = "SELECT a.* FROM TBL_ACCOUNT a " +
            "JOIN TBL_EMPLOYEE e ON a.EMP_NO = e.EMP_NO " +
            "WHERE e.EMP_NAME LIKE '%' || :keyword || '%'", nativeQuery = true)
    Page<ADMAccount> findByEmployee_NameContaining(@Param("keyword") String keyword, Pageable pageable);
//    List<ADMAccount> findByEmployeeNameContaining(@Param("keyword") String keyword);
}
