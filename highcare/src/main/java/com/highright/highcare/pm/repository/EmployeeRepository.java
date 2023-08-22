package com.highright.highcare.pm.repository;

import com.highright.highcare.pm.entity.PmEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import java.util.List;

//@Repository
//@RequiredArgsConstructor
public interface EmployeeRepository extends JpaRepository<PmEmployee, Integer> {


//    List<PmEmployee> findByemployee(String y);

    /* 사원 전체 조회 */
    List<PmEmployee> findAll();

    /* 사원 상세 조회 */
    List<PmEmployee> findByEmpNo(Integer empNo);


//    List<PmEmployee> findByIsResignation(String n);
   List<PmEmployee> findByIsResignation(char isResignation);

    Page<PmEmployee> findByIsResignation(char isResignation,Pageable paging);


    /* 사원 상세 조회 및 검색 */
    List<PmEmployee> findByEmpName(String empName);

    Page<PmEmployee> findByEmpName(String search, Pageable paging);

//    /* 조직도 */
//    public List<PmEmployee> findAll() {
//        return EmployeeRepository.
//    }

//    List<PmEmployee> findByAll();


//    List<Employee> findByEmployee(String y);
    //public class EmployeeRepository extends JpaRepository<PmEmployee, Integer> {
//public class EmployeeRepository {






//    private EntityManager em;
//
//    public EmployeeRepository(EntityManager em){
//        this.em = em;
//    }
//
//    public static PmEmployee findByMemberId(String empNo) {
//    }
//
//
//    public void save(PmEmployee pmemployee) {
//        em.persist(pmemployee);
//    }
//
//    public PmEmployee findOne(Long id){
//        return em.find(PmEmployee.class, id);
//    }
//
//    public List<PmEmployee> findAll() {
//        List<PmEmployee> result = em.createQuery("select p from PmEmployee p", PmEmployee.class)
//                .getResultList();
//        return result;
//    }
//
//    public List<PmEmployee> findByName(String name) {
//        return em.createQuery("select p from PmEmployee p where p.empName = :name", PmEmployee.class)
//                .setParameter("name", name)
//                .getResultList();
//    }


}