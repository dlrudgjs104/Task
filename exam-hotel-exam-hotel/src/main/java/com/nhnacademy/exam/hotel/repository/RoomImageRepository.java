package com.nhnacademy.exam.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhnacademy.exam.hotel.domain.RoomImage;

public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {
}
