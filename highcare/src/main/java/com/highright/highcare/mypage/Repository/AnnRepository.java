package com.highright.highcare.mypage.Repository;

import com.highright.highcare.mypage.entity.MyAnnual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AnnRepository extends JpaRepository<MyAnnual, Integer> {


    List<MyAnnual> findByEmpNo(int empNo);

//    List<MyAnnual> findByAnnApvId(AnnApvId annApvId);



//FETCH FIRST 1 ROWS ONLY
//    @Query(value = "SELECT * FROM TBL_ANNUAL A JOIN TBL_EMPLOYEE B ON(A.EMP_NO = B.EMP_NO) WHERE A.EMP_NO = :empNo " +
//            "ORDER BY A.ANN_NO DESC ",
//            nativeQuery = true)
//    AnnEmployee findByEmpNo(int empNo);
}
