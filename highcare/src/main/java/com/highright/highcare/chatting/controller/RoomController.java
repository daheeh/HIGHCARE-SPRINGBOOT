package com.highright.highcare.chatting.controller;

import com.highright.highcare.chatting.Entity.Chat;
import com.highright.highcare.chatting.Entity.Room;
import com.highright.highcare.chatting.service.ChatService;
import com.highright.highcare.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/socket")
public class RoomController {

    private final ChatService chatService;

    /**
     * 채팅방 참여하기
     * @param roomId 채팅방 id
     */
    @GetMapping("/{roomId}")
//    public ResponseEntity<ResponseDTO> joinRoom(@PathVariable(required = false) Long roomId, Model model) {
    public ResponseEntity<ResponseDTO> joinRoom(@PathVariable Long roomId) {
        System.out.println("Room Controller ===> joinRoom roomId = " + roomId);

        List<Chat> chatList = chatService.findAllChatByRoomId(roomId);
        Room chatRoomId = chatService.findRoomById(roomId);

        Map<String, Object> joinchatRoomMap = new HashMap<>();
        joinchatRoomMap.put("roomId", roomId);
        joinchatRoomMap.put("roomName", chatRoomId);
        joinchatRoomMap.put("chatList", chatList);

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(),  "채팅방 참여 성공" , joinchatRoomMap));
    }

    /**
     * 채팅방 등록
     * @param form
     */
    @PostMapping("/room")
    public ResponseEntity<ResponseDTO> createRoom(@RequestBody RoomForm form) {
        System.out.println("Room Controller ===> createRoom roomForm = " + form);

        Room createRoomName = chatService.createRoom(form.getName());

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(),  "채팅방 등록 성공" , createRoomName));
    }

    /**
     * 채팅방 리스트 보기
     */
    @GetMapping("/roomList")
    public ResponseEntity<ResponseDTO> roomList() {
        List<Room> roomList = chatService.findAllRoom();

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(),  "채팅방 리스트 조회 성공" , roomList));
    }

    /**
     * 방만들기 폼(단순 페이지 이동이라 리액트에 라우팅)
     */
//    @GetMapping("/roomForm")
//    public String roomForm() {
//        return "chat/roomForm";
//    }

}