package com.nhnacademy.exam.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhnacademy.exam.hotel.domain.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
}
