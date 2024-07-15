package com.nhnacademy.exam.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhnacademy.exam.hotel.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	List<Reservation> findAllByUserUserId(Long userId);

	boolean existsByRoomRoomId(Long roomId);
}
