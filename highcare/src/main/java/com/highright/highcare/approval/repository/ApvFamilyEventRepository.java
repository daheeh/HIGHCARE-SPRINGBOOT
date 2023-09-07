package com.highright.highcare.approval.repository;

import com.highright.highcare.approval.entity.ApvFamilyEvent;
import com.highright.highcare.approval.entity.ApvIssuance;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApvFamilyEventRepository extends JpaRepository<ApvFamilyEvent, Long> {

}
