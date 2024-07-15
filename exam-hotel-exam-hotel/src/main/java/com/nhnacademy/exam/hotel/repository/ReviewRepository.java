package com.nhnacademy.exam.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhnacademy.exam.hotel.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
