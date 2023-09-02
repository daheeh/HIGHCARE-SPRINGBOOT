package com.highright.highcare.approval.repository;

import com.highright.highcare.approval.entity.ApvForm;
import com.highright.highcare.approval.entity.ApvFormMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ApvFormRepository extends JpaRepository<ApvForm, Long> {

    ApvForm findByApvNo(Long apvNo);

    @Modifying
    @Query("UPDATE ApvForm af SET af.apvStatus = '결재완료' WHERE af.apvNo = :apvNo")
    void updateApvStatusToPaymentCompleted(@Param("apvNo") Long apvNo);
    // 수신함
    @Query(value = "SELECT AF.* " +
            ", AL.ISAPPROVAL " +
            ", E.EMP_NAME " +
            ", J.JOB_NAME AS jobName " +
            "FROM TBL_APV_FORM AF " +
            "JOIN TBL_APV_LINE AL ON AL.APV_NO = AF.APV_NO " +
            "JOIN TBL_EMPLOYEE E ON AF.EMP_NO = E.EMP_NO " +
            "JOIN TBL_JOB J ON E.JOB_CODE = J.JOB_CODE " +
            "WHERE AL.EMP_NO = :empNo " +
            "AND AL.ISAPPROVAL = :isApproval " +
            "ORDER BY AF.WRITE_DATE " , nativeQuery = true)
    List<ApvForm> findByEmpNoAndApvStatus2(@Param("empNo") int empNo, @Param("isApproval")String isApproval);


    @Query(value = "SELECT AF.* " +
            ", AL.ISAPPROVAL " +
            ", E.EMP_NAME " +
            ", J.JOB_NAME AS jobName " +
            "FROM TBL_APV_FORM AF " +
            "JOIN TBL_APV_LINE AL ON AL.APV_NO = AF.APV_NO " +
            "JOIN TBL_EMPLOYEE E ON AF.EMP_NO = E.EMP_NO " +
            "JOIN TBL_JOB J ON E.JOB_CODE = J.JOB_CODE " +
            "WHERE AL.EMP_NO = :empNo " +
            "AND AF.APV_STATUS = :apvStatus " +
            "ORDER BY AF.WRITE_DATE " , nativeQuery = true)
    List<ApvForm> findByEmpNoAndApvStatus3(@Param("empNo") int empNo, @Param("apvStatus")String apvStatus);

}
