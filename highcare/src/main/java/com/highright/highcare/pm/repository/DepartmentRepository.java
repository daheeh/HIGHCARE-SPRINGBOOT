package com.highright.highcare.pm.repository;


import com.highright.highcare.pm.entity.Departments;
import com.highright.highcare.pm.entity.Employees;
import com.highright.highcare.pm.entity.PmDepartment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Departments, Integer> {
//    List<PmDepartment> findAll();//?

    //@EntityGraph 데이터베이스에서 부서 엔티티와 그 부서에 속한 직원 엔티티를 함께 가져오는 방법
    // 엔티티와 연관된 다른 엔티티들을 로드할때, 지연로딩을 최적화하는 방법
    @EntityGraph(attributePaths = "employeesList")
    List<Departments> findAll();


}
