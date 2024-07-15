package com.nhnacademy.exam.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhnacademy.exam.hotel.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
