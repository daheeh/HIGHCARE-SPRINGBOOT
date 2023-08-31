package com.highright.highcare.approval.repository;

import com.highright.highcare.approval.entity.ApvFormMain;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface ApvFormMainRepository extends JpaRepository<ApvFormMain, Long> {

    List<ApvFormMain> findByEmpNoAndApvStatus(@Param("empNo")int empNo, @Param("apvStatus")String apvStatus);
    Page<ApvFormMain> findByEmpNoAndApvStatus(@Param("empNo")int empNo, @Param("apvStatus")String apvStatus, Pageable paging);

    int countByEmpNoAndApvStatus(@Param("empNo")int empNo, @Param("apvStatus")String 결재진행중);

    int countByEmpNoAndIsUrgency(@Param("empNo")int empNo, @Param("isUrgency")String T);

    @Query(value = "SELECT A.* " +
            "FROM TBL_APV_FORM A " +
            "WHERE A.EMP_NO = :empNo " +
            "ORDER BY A.WRITE_DATE DESC ", nativeQuery = true)
    List<ApvFormMain> findTitlesByEmpNo(@Param("empNo")int empNo);

    @Query(value = "SELECT AF.* " +
            ", E.NAME " +
            ", J.NAME " +
            "FROM TBL_APV_FORM AF " +
            "JOIN TBL_APV_LINE AL ON AL.APV_NO = AF.APV_NO " +
            "JOIN TBL_EMPLOYEE E ON AF.EMP_NO = E.EMP_NO " +
            "JOIN TBL_JOB J ON E.JOB_CODE = J.JOB_CODE " +
            "WHERE AL.EMP_NO = :empNo " +
            "AND AL.ISAPPROVAL = 'F'" +
            "AND AF.APV_STATUS = :apvStatus " , nativeQuery = true)
    List<ApvFormMain> findByEmpNoAndApvStatus2(@Param("empNo") int empNo, @Param("apvStatus")String apvStatus);


}
