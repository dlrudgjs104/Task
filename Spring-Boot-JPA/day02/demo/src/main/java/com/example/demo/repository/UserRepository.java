package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAllByUserBirthEquals(String userBirth);

    List<User> findAllByUserNameContaining(String name);

    List<User> findAllByUserPointBetween(int min, int max);

    List<User> findAllByCreatedAtBefore(LocalDateTime basisDateTime);

    List<User> findAllByCreatedAtAfter(LocalDateTime basisDateTime);

    List<User> findAllByCreatedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<User> findAllByLatestLoginAtAfter(LocalDateTime basisDateTime);

    List<User> findAllByLatestLoginAtBefore(LocalDateTime basisDateTime);

    List<User> findAllByLatestLoginAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);



}
