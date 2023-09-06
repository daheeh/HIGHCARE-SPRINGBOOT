package com.highright.highcare.chatting.storage;

import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@ToString
public class UserStorage {
    private static UserStorage instance;
    private Set<String> users;

    private UserStorage() {
        users = new HashSet<>();
    }

    public static synchronized UserStorage getInstance() {
        if (instance == null) {
            instance = new UserStorage();
        }
        return instance;
    }

    public Set<String> getUsers() {
        return users;
    }

    public boolean userExists(String userId) {
        return users.contains(userId);
    }

    public void addUser(String userId) {
        users.add(userId);
        System.out.println("Added user====================> " + userId);
    }

    public void removeUser(String userId) {
        users.remove(userId);
    }

}
