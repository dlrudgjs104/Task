package com.nhnacademy.customerservice.repository;

import com.nhnacademy.customerservice.domain.User;

import java.util.List;

public interface UserRepository {
    boolean exists(String id);

    boolean matches(String id, String password);

    List<User> getUsers();

    User getUser(String id);

    boolean checkAdmin(String id);
}
