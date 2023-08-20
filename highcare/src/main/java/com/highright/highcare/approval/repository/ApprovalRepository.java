package com.highright.highcare.approval.repository;

import com.highright.highcare.approval.entity.ApvForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ApprovalRepository extends JpaRepository<ApvForm, Long> {

    List<ApvForm> findByEmpNo(int empNo);

    @Query(value = "SELECT SEQ_APV_NO.NEXTVAL FROM DUAL", nativeQuery = true)
    Long getNextApvNo();
}
