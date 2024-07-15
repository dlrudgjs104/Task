package com.nhnacademy.exam.hotel.service;

import org.springframework.stereotype.Service;

import com.nhnacademy.exam.hotel.domain.Reservation;
import com.nhnacademy.exam.hotel.domain.Room;
import com.nhnacademy.exam.hotel.domain.User;
import com.nhnacademy.exam.hotel.dto.request.ReservationCreateRequest;
import com.nhnacademy.exam.hotel.dto.response.ReservationCreateResponse;
import com.nhnacademy.exam.hotel.exception.AlreadyReservationException;
import com.nhnacademy.exam.hotel.exception.ExceededReservationException;
import com.nhnacademy.exam.hotel.repository.ReservationRepository;
import com.nhnacademy.exam.hotel.repository.RoomRepository;
import com.nhnacademy.exam.hotel.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final RoomRepository roomRepository;
	private final UserRepository userRepository;
	private final int maxReservationCount = 3;

	public ReservationCreateResponse createReservation(Long roomId, ReservationCreateRequest request) {
		Long userId = 1L;

		if (reservationRepository.existsByRoomRoomId(roomId)) {
			throw new AlreadyReservationException();
		}

		int count = reservationRepository.findAllByUserUserId(userId).size();
		if (count > maxReservationCount) {
			throw new ExceededReservationException();
		}

		Room room = roomRepository.findById(roomId).orElseThrow();
		User user = userRepository.findById(userId).orElseThrow();

		Reservation reservation = reservationRepository.save(Reservation.toEntity(request, user, room));
		return ReservationCreateResponse.fromEntity(reservation);
	}
}
