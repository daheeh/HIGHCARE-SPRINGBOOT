package com.highright.highcare.admin.repository;

import com.highright.highcare.auth.entity.ADMEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<ADMEmployee, Integer> {
}
