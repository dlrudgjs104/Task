package com.nhnacademy.exam.hotel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.exam.hotel.dto.request.ReservationCreateRequest;
import com.nhnacademy.exam.hotel.dto.response.ReservationCreateResponse;
import com.nhnacademy.exam.hotel.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationService reservationService;

	@PostMapping("/v1/hotel-api/reservations/{roomId}")
	public ResponseEntity<ReservationCreateResponse> createReservation(@PathVariable Long roomId,
		@RequestBody ReservationCreateRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(reservationService.createReservation(roomId, request));
	}

}