package com.highright.highcare.chatting.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChatMessage {

    private Long roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
    private LocalDateTime time; // 채팅 보낸 시간

}
