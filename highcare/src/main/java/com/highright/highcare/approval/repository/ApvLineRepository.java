package com.highright.highcare.approval.repository;

import com.highright.highcare.approval.entity.ApvLine;
import com.highright.highcare.approval.entity.ApvMeetingLog;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApvLineRepository extends JpaRepository<ApvLine, Long> {

}
