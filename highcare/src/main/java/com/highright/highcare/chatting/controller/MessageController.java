package com.highright.highcare.chatting.controller;


import com.highright.highcare.chatting.dto.Conversation;
import com.highright.highcare.chatting.dto.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private RedisTemplate<String, Conversation> conversationTemplate;


    //  발신자의 이름을 to로 지정하고, 이 메소드를 호출하면 발신자의 이름으로 메시지가 처리
    @MessageMapping("/send/{to}")
    public void sendMessage(@DestinationVariable String to, MessageModel message){
        System.out.println("handling send message: " + message + "to: " + to);

    }


    //클라이언트에서 보낸 메시지를 받아와서, 발신자와 수신자의 아이디로 처리
    @MessageMapping("/send")
    public void SendToMessage(MessageModel msg){
        logger.info("{}", msg);
        HashOperations<String, String, Conversation> ho = conversationTemplate.opsForHash();
        // 내가 상대방과 대화 데이터가 있을때 대화 정보가 이미 있으면 이어서 대화에 메시지를 추가
        if(ho.hasKey(msg.getAuthor(),msg.getTo())){
            Conversation con = ho.get(msg.getAuthor(), msg.getTo());
            con.getMessageList().add(msg);
            ho.put(msg.getAuthor(),msg.getTo(),con);
        } else {
            // 상대방과 대화 데이터가 없을때 새로운 대화 정보 생성
            Conversation newCon = new Conversation(msg.getTo(), new ArrayList<>());
            newCon.getMessageList().add(msg);
            ho.put(msg.getAuthor(),msg.getTo(),newCon);
        }

        if(ho.hasKey(msg.getTo(), msg.getAuthor())){
            // 상대방에게 나와의 대화 데이터가 있을때
            Conversation con = ho.get(msg.getTo(), msg.getAuthor());
            con.getMessageList().add(msg);
            ho.put(msg.getTo(), msg.getAuthor(),con);
        } else {
            // 상대방에게 나와의 대화 데이터가 없을때
            Conversation newCon = new Conversation(msg.getAuthor(), new ArrayList<>());
            newCon.getMessageList().add(msg);
            ho.put(msg.getTo(), msg.getAuthor(),newCon);
        }
        simpMessagingTemplate.convertAndSend("/topic/"+msg.getTo() , msg); //메세지를 상대방에게 전달
        System.out.println("MessageController 받은 메세지 상대방에게 전달할때 주소" + msg.getTo());
    }

    @MessageMapping("/Template")
    public void SendTemplateMessage() {
        simpMessagingTemplate.convertAndSend("/topics/template" , "Template");
    }

    @RequestMapping(value="/api")
    public void SendAPI() {
        simpMessagingTemplate.convertAndSend("/topics/api" , "API");
    }


    private MessageHeaders createHeaders(@Nullable String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        if (sessionId != null) headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        logger.info(headerAccessor.toString());
        return headerAccessor.getMessageHeaders();
    }

}
