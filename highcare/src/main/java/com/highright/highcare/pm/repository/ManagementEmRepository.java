package com.highright.highcare.pm.repository;

import com.highright.highcare.pm.entity.Management;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagementEmRepository extends JpaRepository<Management, Integer> {

    /* 출 퇴근 조회 */
    Page<Management> findByManNo(String manNo, Pageable paging);

    List<Management> findAll();
}
