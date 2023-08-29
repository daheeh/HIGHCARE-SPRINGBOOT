package com.highright.highcare.approval.repository;

import com.highright.highcare.approval.entity.ApvBusinessTrip;
import com.highright.highcare.approval.entity.ApvExpForm;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApvExpFormRepository extends JpaRepository<ApvExpForm, Long> {

}
