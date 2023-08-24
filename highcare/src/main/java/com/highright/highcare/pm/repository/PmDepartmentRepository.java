package com.highright.highcare.pm.repository;


import com.highright.highcare.pm.entity.PmDepartment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PmDepartmentRepository extends JpaRepository<PmDepartment, Integer> {
//    List<PmDepartment> findAll();//?
    @EntityGraph(attributePaths = "employees")
    List<PmDepartment> findAll();
}
