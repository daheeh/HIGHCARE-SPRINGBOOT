package com.highright.highcare.admin.repository;

import com.highright.highcare.auth.entity.ADMEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ADMEmployeeRepository extends JpaRepository<ADMEmployee, Integer> {

//    @Query("SELECT e FROM ADMEmployee e WHERE e.empNo NOT IN (SELECT a.empNo FROM Account a)")
//    List<ADMEmployee> findADMEmployeesWithoutAccount();

}
