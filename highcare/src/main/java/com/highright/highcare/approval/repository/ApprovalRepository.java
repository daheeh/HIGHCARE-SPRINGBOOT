package com.highright.highcare.approval.repository;

import com.highright.highcare.approval.entity.ApvForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalRepository extends JpaRepository<ApvForm, Integer> {

    List<ApvForm> findByEmpNo(int empNo);
    }
