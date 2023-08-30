package com.highright.highcare.approval.repository;

import com.highright.highcare.approval.entity.ApvForm;
import com.highright.highcare.approval.entity.ApvFormMain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ApvFormMainRepository extends JpaRepository<ApvFormMain, Long> {

    List<ApvFormMain> findByEmpNoAndApvStatus(int empNo, String apvStatus);
    Page<ApvFormMain> findByEmpNoAndApvStatus(int empNo, String apvStatus, Pageable paging);

    int countByEmpNoAndApvStatus(int empNo, String 결재진행중);

    int countByEmpNoAndIsUrgency(int empNo, String t);

    @Query(value = "SELECT A.* " +
            "FROM TBL_APV_FORM A " +
            "WHERE A.EMP_NO = :empNo " +
            "ORDER BY A.WRITE_DATE DESC ", nativeQuery = true)
    List<ApvFormMain> findTitlesByEmpNo(int empNo);

    @Query(value = "SELECT AF.* " +
            "FROM TBL_APV_FORM AF " +
            "JOIN TBL_APV_LINE AL ON AF.APV_NO = AL.APV_NO " +
            "WHERE AF.EMP_NO = :empNo " +
            "AND AL.ISAPPROVAL = 'F'", nativeQuery = true)
    List<ApvFormMain> findApprovedTitlesByEmpNo(int empNo);
}
