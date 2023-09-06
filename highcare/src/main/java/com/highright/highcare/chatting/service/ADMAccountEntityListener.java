package com.highright.highcare.chatting.service;

import com.highright.highcare.auth.entity.ADMAccount;
import org.springframework.stereotype.Component;

import javax.persistence.PostUpdate;

@Component
public class ADMAccountEntityListener {
    private UserService userService;

    // 엔티티 업데이트 될 때마다 데이터 동기화
    @PostUpdate
    public void onPostUpdate(ADMAccount account) {

        userService.syncUsersFromDatabase();
    }

}
