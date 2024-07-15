package com.nhnacademy.exam.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhnacademy.exam.hotel.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
