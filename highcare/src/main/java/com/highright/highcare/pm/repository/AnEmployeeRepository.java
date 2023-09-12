package com.highright.highcare.pm.repository;

import com.highright.highcare.pm.entity.AnAnual;
import com.highright.highcare.pm.entity.AnEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnEmployeeRepository extends JpaRepository<AnEmployee, Integer> {

    Page<AnEmployee> findAll(Pageable pageable);
}
