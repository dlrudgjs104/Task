package com.nhnacademy.exam.hotel.exception;

public class AlreadyReservationException extends RuntimeException {
	public AlreadyReservationException() {
		super("이미 예약된 방입니다.");
	}
}
