package com.highright.highcare.chatting.repository;

import com.highright.highcare.chatting.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}