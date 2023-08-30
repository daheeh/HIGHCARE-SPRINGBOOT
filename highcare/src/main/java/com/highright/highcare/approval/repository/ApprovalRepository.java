package com.highright.highcare.approval.repository;

import com.highright.highcare.approval.entity.ApvForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ApprovalRepository extends JpaRepository<ApvForm, Long> {

//    @Query("SELECT af FROM ApvForm af LEFT JOIN FETCH af.apvBusinessTrips WHERE af.empNo = :empNo AND af.title = :title")
//    List<ApvForm> findApvFormsWithBusinessTrips(@Param("empNo") int empNo, @Param("title") String title);
    List<ApvForm> findByEmpNoAndApvStatus(int empNo, String apvStatus);
    Page<ApvForm> findByEmpNoAndApvStatus(int empNo, String apvStatus, Pageable paging);
    List<ApvForm> findByEmpNoAndTitle(int empNo, String title);

    int countByEmpNoAndApvStatus(int empNo, String 결재진행중);

    int countByEmpNoAndIsUrgency(int empNo, String t);

    List<ApvForm> findByEmpNo(int empNo);
//    List<ApvForm> findByEmpNoOrderByWriteDateDesc(int empNo);

//    @Query("SELECT DISTINCT a FROM ApvForm a WHERE a.empNo = :empNo ORDER BY a.writeDate DESC")
//    List<ApvForm> findDistinctByEmpNoOrderByWriteDateDesc(@Param("empNo") int empNo);

    @Query(value = "SELECT A.* " +
            "FROM TBL_APV_FORM A " +
            "WHERE A.EMP_NO = :empNo " +
            "ORDER BY A.WRITE_DATE DESC ", nativeQuery = true)
    List<ApvForm> findTitlesByEmpNo(int empNo);
}
