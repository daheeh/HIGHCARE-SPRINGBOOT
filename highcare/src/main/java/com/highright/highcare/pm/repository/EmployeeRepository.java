package com.highright.highcare.pm.repository;

import com.highright.highcare.auth.entity.Employee;
import com.highright.highcare.pm.entity.PmEmployee;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//@Repository
//@RequiredArgsConstructor
public interface EmployeeRepository extends JpaRepository<PmEmployee, Integer> {

//    List<PmEmployee> findByemployee(String y);

    List<PmEmployee> findByEmpNo(String empNo);


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