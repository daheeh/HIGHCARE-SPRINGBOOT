package com.highright.highcare.bulletin.repository;

import com.highright.highcare.bulletin.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findAll();
}
