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

    @Modifying
    @Query("UPDATE ApvForm af SET af.apvStatus = '결재반려' WHERE af.apvNo = :apvNo")
    void updateApvStatusToReject(@Param("apvNo") Long apvNo);

    @Modifying
    @Query(value = "UPDATE TBL_APV_LINE AL " +
                    "SET AL.ISAPPROVAL = 'F' " +
                    "WHERE AL.APV_NO = :apvNo " +
                    "AND AL.ISAPPROVAL <> 'F'", nativeQuery = true)
    void updateIsApprovalToFalse(@Param("apvNo") Long apvNo);


}
