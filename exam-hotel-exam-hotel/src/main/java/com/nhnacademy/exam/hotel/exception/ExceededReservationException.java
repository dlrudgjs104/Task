package com.nhnacademy.exam.hotel.exception;

public class ExceededReservationException extends RuntimeException {
	public ExceededReservationException() {
		super("하루에 최대 3개의 객실만 예약할 수 있습니다.");
	}
}
