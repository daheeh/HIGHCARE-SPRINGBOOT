package com.highright.highcare.reservation.repository;

import com.highright.highcare.reservation.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRespository extends JpaRepository<Resource, Integer> {
}
