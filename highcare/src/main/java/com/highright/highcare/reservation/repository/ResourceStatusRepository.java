package com.highright.highcare.reservation.repository;

import com.highright.highcare.reservation.entity.ResourceReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface ResourceStatusRepository extends JpaRepository<ResourceReservationStatus, Integer> {
    List<ResourceReservationStatus> findAllByReservationDateAndReservationStatus(Date reservationDate, String approval);
}
