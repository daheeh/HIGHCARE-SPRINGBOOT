package com.highright.highcare.admin.repository;

import com.highright.highcare.admin.entity.Menu;
import com.highright.highcare.admin.entity.MenuMapping;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MenuRepository<T extends MenuMapping> extends JpaRepository<Menu, Integer> {

    void deleteByGroupCodeAndId(String groupCode, String id);

//    Optional<List<MenuMapping>> findById(String id);

//    @EntityGraph(attributePaths = "menuGroup")
    List<T> findAllById(@Param("id") String id);

    void deleteAllById(@Param("id") String id);

    @EntityGraph(attributePaths = "menuGroup.groupCode")
    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.menuGroup WHERE m.id = :id")
    List<Menu> findById(@Param("id") String id);

}
