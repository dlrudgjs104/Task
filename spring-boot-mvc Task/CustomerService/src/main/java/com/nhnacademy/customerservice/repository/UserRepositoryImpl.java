package com.nhnacademy.customerservice.repository;

import com.nhnacademy.customerservice.domain.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> userMap = new HashMap<>();

    public UserRepositoryImpl() {
        userMap.put("1", new User("1", "1", "1", User.Auth.AUTH_ADMIN));
        userMap.put("jake", new User("jake", "1", "Jake", User.Auth.AUTH_ADMIN));
        userMap.put("tom", new User("tom", "1234", "Tom", User.Auth.AUTH_USER));
        userMap.put("ethan", new User("ethan", "1", "Ethan", User.Auth.AUTH_USER));
    }

    @Override
    public boolean exists(String id) {
        return userMap.containsKey(id);
    }

    @Override
    public boolean matches(String id, String password) {
        return Optional.ofNullable(getUser(id))
                .map(User -> User.getPassword().equals(password))
                .orElse(false);
    }

    @Override
    public List<User> getUsers() {
        return userMap.values().stream().toList();
    }

    @Override
    public User getUser(String id) {
        return userMap.get(id);
    }

    @Override
    public boolean checkAdmin(String id) {
        return userMap.get(id).getAuth() == User.Auth.AUTH_ADMIN;
    }

}
