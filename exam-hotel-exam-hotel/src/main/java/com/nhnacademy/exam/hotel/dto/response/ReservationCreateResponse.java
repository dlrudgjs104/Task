package com.nhnacademy.exam.hotel.dto.response;

import com.nhnacademy.exam.hotel.domain.Reservation;

import lombok.Builder;

@Builder
public class ReservationCreateResponse {
	Long id;

	public static ReservationCreateResponse fromEntity(Reservation reservation) {
		return ReservationCreateResponse.builder()
			.id(reservation.getReservationId())
			.build();
	}
}
