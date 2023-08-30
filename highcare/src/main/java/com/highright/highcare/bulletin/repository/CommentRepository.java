package com.highright.highcare.bulletin.repository;

import com.highright.highcare.bulletin.entity.Board;
import com.highright.highcare.bulletin.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByBoard(Board board);
}
