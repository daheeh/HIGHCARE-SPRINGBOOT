package com.highright.highcare.service;

import com.highright.highcare.chatting.Entity.Chat;
import com.highright.highcare.chatting.Entity.Room;
import com.highright.highcare.chatting.repository.ChatRepository;
import com.highright.highcare.chatting.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ChatServiceTest {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    RoomRepository roomRepository;

    void redisTest() {
        Room room = Room.createRoom("room1");
        roomRepository.save(room);
        Chat chat = Chat.createChat(room, "다희", "안녕하세요~");
        chatRepository.save(chat);

        Chat findChat = chatRepository.findById(chat.getId()).get();

        System.out.println("findChat :"  + findChat);
        System.out.println("findChat.getRoom() : " + findChat.getRoom());

        List<Chat> rooms = chatRepository.findAllByRoomId(room.getId());
        System.out.println("rooms : " + rooms);
    }

}
