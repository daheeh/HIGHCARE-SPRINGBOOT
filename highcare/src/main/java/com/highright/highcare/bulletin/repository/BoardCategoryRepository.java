package com.highright.highcare.bulletin.repository;

import com.highright.highcare.bulletin.entity.BulletinCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCategoryRepository extends JpaRepository<BulletinCategories, Integer> {
    BulletinCategories findByNameBoard(String nameBoard);
}
