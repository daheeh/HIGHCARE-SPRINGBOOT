package com.highright.highcare.chatting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 채팅 메세지를 주고받기 위한 DTO
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatDTO {

    // 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
    private String time; // 채팅 보낸 시간

}
