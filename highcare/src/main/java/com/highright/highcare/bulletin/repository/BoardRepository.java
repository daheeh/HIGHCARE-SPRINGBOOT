package com.highright.highcare.bulletin.repository;

import com.highright.highcare.bulletin.entity.Board;
import com.highright.highcare.bulletin.entity.BulletinCategories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findAll();

    List<Board> findByDeleteYnAndBulletinCategories(char n, BulletinCategories byCategoryCode);

    List<Board> findByDeleteYn(char n);
}
