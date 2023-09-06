package com.highright.highcare.chatting.controller;


import com.highright.highcare.chatting.dto.Conversation;
import com.highright.highcare.chatting.dto.MessageModel;
import com.highright.highcare.chatting.dto.User;
import com.highright.highcare.chatting.service.UserService;
import com.highright.highcare.chatting.storage.UserStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private RedisTemplate<String, Conversation> conversationTemplate;

    @Autowired
    private UserStorage userStorage;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        userService.syncUsersFromDatabase();
    }

    @GetMapping("/search/{myId}/{userId}")
    public ResponseEntity<Integer> search(@PathVariable String userId, @PathVariable String myId){
        System.out.println("myId=============>" + myId + " userId============> " + userId);
        System.out.println("userStorage =============> " + userStorage);

        if(!userStorage.userExists(userId)){
            return new ResponseEntity<>(101, HttpStatus.OK); // 존재하지 않는 사용자
        }
        System.out.println("UsersController /search/{myId}/{userId} ====================> " + userId);

        HashOperations<String, String, Conversation> hashOperations = conversationTemplate.opsForHash();

        if(hashOperations.hasKey(myId, userId)){
            return new ResponseEntity<>(102, HttpStatus.OK);    // 이미 추가된 사용자
        }

        hashOperations.put(myId, userId, new Conversation(userId, new ArrayList<>()));
        return new ResponseEntity<>(100, HttpStatus.OK);    //  성공
    }

    @GetMapping("/fetchAllUsers/{userId}")
    public ResponseEntity<Collection<String>> fetchAll(@PathVariable String userId){
//        userService.syncUsersFromDatabase();

        HashOperations<String, String, Conversation> hashOperations = conversationTemplate.opsForHash();
        logger.info("user id: {}", userId);
        Set<String> users = hashOperations.keys(userId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @DeleteMapping("/leaveChat")
    public ResponseEntity<Boolean> leaveChat(@RequestBody MessageModel messageModel){
//        userService.syncUsersFromDatabase();

        HashOperations<String, String, Conversation> hashOperations = conversationTemplate.opsForHash();
        logger.info("Leave chat host: {} , partner: {}", messageModel.getAuthor(),messageModel.getTo());
        hashOperations.delete(messageModel.getAuthor(), messageModel.getTo());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
