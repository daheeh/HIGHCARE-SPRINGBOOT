package com.highright.highcare.mypage.Repository;

import com.highright.highcare.mypage.entity.AnnEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AnnRepository extends JpaRepository<AnnEmployee, Integer> {

//    @Query(value = "SELECT * FROM AnnEmployee ORDER BY annNo DESC LIMIT 1", nativeQuery = true)
    AnnEmployee findByEmpNo(int empNo);
}
