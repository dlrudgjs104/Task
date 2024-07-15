package com.nhnacademy.exam.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhnacademy.exam.hotel.domain.Point;

public interface PointRepository extends JpaRepository<Point, Long> {
}
