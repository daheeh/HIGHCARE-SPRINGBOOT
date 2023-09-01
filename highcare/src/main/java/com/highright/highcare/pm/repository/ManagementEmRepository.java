package com.highright.highcare.pm.repository;

import com.highright.highcare.pm.entity.Management;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ManagementEmRepository extends JpaRepository<Management, Integer> {

    /* 출 퇴근 조회 */
    Page<Management> findByManNo(String manNo, Pageable paging);

    List<Management> findAll();

    List<Management> findByManDateAndEmpNo(String manDate, Integer empNo);

    /* 퇴근 조회용 */
    Page<Management> findByEmpNo(int empNo, Pageable paging);

    Page<Management> findByEmpNo(int empNo, String manDate, Pageable paging);




}
