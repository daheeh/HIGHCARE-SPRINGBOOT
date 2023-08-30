package com.highright.highcare.chatting.pubsub;

import com.highright.highcare.chatting.dto.ChatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatDTO message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
