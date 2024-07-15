package com.nhnacademy.exam.hotel.dto.request;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ReservationCreateRequest {
	LocalDateTime reservationCheckInDate;
	LocalDateTime reservationCheckOutDate;
}
