package com.highright.highcare.pm.repository;

//import com.highright.highcare.pm.entity.Employees;
import com.highright.highcare.pm.dto.PmEmployeeDTO;
//import com.highright.highcare.pm.entity.AnEmployee;
import com.highright.highcare.pm.entity.MgEmployee;
import com.highright.highcare.pm.entity.PmEmployee;
import com.highright.highcare.pm.entity.ReEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.highright.highcare.pm.entity.PmDepartment;

import javax.persistence.EntityManager;
import java.util.List;

//@Repository
//@RequiredArgsConstructor
public interface EmployeeRepository extends JpaRepository<PmEmployee, Integer> {


    /* 사원 전체 조회 */
    List<PmEmployee> findAll();

    /* 사원 상세 조회 */
    List<PmEmployee> findByEmpNo(Integer empNo);

   List<PmEmployee> findByIsResignation(char isResignation);

    Page<PmEmployee> findByIsResignation(char isResignation,Pageable paging);

    /* 사원 번호 조회 */
    PmEmployee findByEmpNo(int empNo);

    /* 사원 상세 조회 및 검색 */
    List<PmEmployee> findByEmpName(String empName);


    Page<PmEmployee> findByEmpNo(int empNo, Pageable paging);

    Page<PmEmployee> findByEmpName(String search, Pageable paging);

    Page<PmEmployee> findByIsResignation(String empNo, Pageable paging);

//    Page<AnEmployee> findByannaul(int empNo, Pageable paging);
}