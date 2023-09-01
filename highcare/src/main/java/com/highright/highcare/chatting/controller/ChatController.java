package com.highright.highcare.chatting.controller;
import com.highright.highcare.chatting.Entity.Chat;
import com.highright.highcare.chatting.dto.ChatMessage;
import com.highright.highcare.chatting.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;



@Controller
@RequiredArgsConstructor
public class ChatController {
    // WebSocket을 통해 채팅을 주고받고, 리액트의 웹소켓과 연결되어 있기 때문에 ChatRoomController처럼 ResponseEntity를 사용할 필요가 업슴
    private final ChatService chatService;

    @MessageMapping("/{roomId}") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    @SendTo("/room/{roomId}")   //구독하고 있는 장소로 메시지 전송 (목적지)  -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
    public ChatMessage test(@DestinationVariable Long roomId, ChatMessage message) {

        //채팅 저장
        Chat chat = chatService.createChat(roomId, message.getSender(), message.getMessage());
        return ChatMessage.builder()
                .roomId(roomId)
                .sender(chat.getSender())
                .message(chat.getMessage())
                .build();
    }

}