//package com.highright.highcare.chatting.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.CrossOrigin;
//
//import javax.websocket.OnClose;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//
//// 클라이언트가 접속될 때마다 생성되어 클라이언트와 직접 통신하는 클래스
//// 클라이언트가 접속할 때마다 session 관련 정보를 정적으로 저장하여, 1:N 통신이 가능하도록함
//
//@Service
//@ServerEndpoint("/socket/chatting")
//@CrossOrigin(origins = "http://localhost:3000")
//public class WebSocketChatSession {
//    private static Set<Session> clients =
//            Collections.synchronizedSet(new HashSet<Session>());
//    private static Logger logger = LoggerFactory.getLogger(WebSocketChatSession.class);
//
//    // 클라이언트가 접속할 때마다 실행
//    @OnOpen
//    public void onOpen(Session session) throws Exception {
//        logger.info("open session : {}, clients={}", session.toString(), clients);
//
//
//        if(!clients.contains(session)) {
//            clients.add(session);
//            logger.info("==========================session open : {}", session);
//        }else{
//            logger.info("이미 연결된 session");
//        }
//    }
//
//    // 메세지 수신 시
//    @OnMessage
//    public void onMessage(String message, Session session) throws IOException {
//        logger.info("receive message : {}", message);
//
//        for (Session s : clients) {
//            logger.info("send data : {}", message);
//
//            s.getBasicRemote().sendText(message);
//        }
//
//        //클라이언트와 서버Socket이 연결된 상태에서, 메세지가 전달되면 해당 메서드가 실행되어 상수인 clients에 있는 모든 session에게 메세지를 전달
//    }
//
//    // 클라이언트가 접속을 종료할 시
//    @OnClose
//    public void onClose(Session session) {
//
//        logger.info("session close : {}", session);
//        clients.remove(session);
//    }
//
//}
