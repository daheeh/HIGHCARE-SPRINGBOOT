package com.highright.highcare.pm.repository;


import com.highright.highcare.pm.entity.Departments;
import com.highright.highcare.pm.entity.PmDepartment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Departments, Integer> {
//    List<PmDepartment> findAll();//?
    @EntityGraph(attributePaths = "employees")
    List<Departments> findAll();
}
