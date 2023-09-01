package com.highright.highcare.chatting.repository;

import com.highright.highcare.chatting.Entity.Chat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatRepository extends CrudRepository<Chat, Long> {
    List<Chat> findAllByRoomId(Long roomId);
}
