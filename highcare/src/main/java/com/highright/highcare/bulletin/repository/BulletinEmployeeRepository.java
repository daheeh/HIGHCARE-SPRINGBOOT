package com.highright.highcare.bulletin.repository;

import com.highright.highcare.bulletin.entity.BulletinEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulletinEmployeeRepository extends JpaRepository<BulletinEmployee, Integer> {
}
