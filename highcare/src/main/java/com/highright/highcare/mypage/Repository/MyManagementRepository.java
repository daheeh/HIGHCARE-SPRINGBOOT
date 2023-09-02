package com.highright.highcare.mypage.Repository;

import com.highright.highcare.mypage.entity.MyManegement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyManagementRepository extends JpaRepository <MyManegement, Integer> {
    List<MyManegement> findByEmpNo(int empNo);
}
