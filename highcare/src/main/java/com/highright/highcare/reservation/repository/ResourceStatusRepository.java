package com.highright.highcare.reservation.repository;

import com.highright.highcare.reservation.entity.Resource;
import com.highright.highcare.reservation.entity.ResourceReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.sql.Date;
import java.util.List;

public interface ResourceStatusRepository extends JpaRepository<ResourceReservationStatus, Integer> {
    List<ResourceReservationStatus> findAllByReservationDateAndReservationStatus(String reservationDate, String approval);


//    List<ResourceReservationStatus> findByReservationDateAndStartTimeOrEndTimeOrStartTimeLessThanAndEndTimeGreaterThanOrStartTimeLessThanAndEndTimeGreaterThanEqualOrStartTimeGreaterThanAndStartTimeLessThan(Date reservationDate, String startTime, String endTime, String startTime1, String startTime2, String endTime1, String endTime2, String startTime3, String endTime3);
    @Query(value = "SELECT A.* " +
    "FROM TBL_RESOURCE_RESERVATION_STATUS A " +
    "WHERE A.RESERVATION_DATE = :reservationDate AND A.RESERVATION_STATUS = 'APPROVAL' AND A.RESOURCE_CODE =:resourceCode " +
    "AND (A.START_TIME = :startTime OR A.END_TIME = :endTime " +
    "OR A.START_TIME < :startTime AND A.END_TIME > :startTime " +
    "OR A.START_TIME < :endTime AND A.END_TIME >= :endTime " +
    "OR A.START_TIME > :startTime AND A.START_TIME < :endTime) ", nativeQuery = true)
    List<ResourceReservationStatus> findByresList(@Param("reservationDate") String reservationDate, @Param("startTime") String startTime,@Param("endTime") String endTime,@Param("resourceCode") int resourceCode);

    List<ResourceReservationStatus> findAllByReservationDateAndReservationStatusAndResource(String reservationDate, String approval, Resource resource);
}
