package com.highright.highcare.approval.repository;

import com.highright.highcare.approval.entity.ApvExpForm;
import com.highright.highcare.approval.entity.ApvVacation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApvVacationRepository extends JpaRepository<ApvVacation, Long> {

}
