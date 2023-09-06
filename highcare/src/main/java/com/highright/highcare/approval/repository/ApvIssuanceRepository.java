package com.highright.highcare.approval.repository;

import com.highright.highcare.approval.entity.ApvIssuance;
import com.highright.highcare.approval.entity.ApvVacation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApvIssuanceRepository extends JpaRepository<ApvIssuance, Long> {

}
