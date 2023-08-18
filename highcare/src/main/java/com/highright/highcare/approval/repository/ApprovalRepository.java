package com.highright.highcare.approval.repository;

import com.highright.highcare.approval.entity.ApvForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRepository extends JpaRepository<ApvForm, Integer> {

}
