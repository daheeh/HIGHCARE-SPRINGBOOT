package com.highright.highcare.approval.repository;

import com.highright.highcare.approval.entity.ApvForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ApvFormRepository extends JpaRepository<ApvForm, Long> {

    ApvForm findByApvNo(Long apvNo);

    @Modifying
    @Query("UPDATE ApvForm af SET af.apvStatus = '결재완료' WHERE af.apvNo = :apvNo")
    void updateApvStatusToPaymentCompleted(@Param("apvNo") Long apvNo);
}
