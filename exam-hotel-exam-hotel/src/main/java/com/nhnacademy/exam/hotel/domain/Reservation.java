package com.nhnacademy.exam.hotel.domain;

import java.time.LocalDateTime;

import com.nhnacademy.exam.hotel.dto.request.ReservationCreateRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "reservations")
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "resevation_id")
	private Long reservationId;

	@Column(name = "resevation_check_in_date", nullable = false)
	private LocalDateTime reservationCheckInDate;

	@Column(name = "resevation_chech_out_date", nullable = false)
	private LocalDateTime reservationCheckOutDate;

	@Column(name = "resevation_check", nullable = false)
	private boolean reservationCheck = false;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(optional = false)
	@JoinColumn(name = "room_id")
	private Room room;

	@Builder
	public Reservation(Long reservationId, LocalDateTime reservationCheckInDate, LocalDateTime reservationCheckOutDate,
		User user, Room room) {
		this.reservationId = reservationId;
		this.reservationCheckInDate = reservationCheckInDate;
		this.reservationCheckOutDate = reservationCheckOutDate;
		this.user = user;
		this.room = room;
	}

	public static Reservation toEntity(ReservationCreateRequest request, User user, Room room) {
		return Reservation.builder()
			.reservationCheckInDate(request.getReservationCheckInDate())
			.reservationCheckOutDate(request.getReservationCheckOutDate())
			.user(user)
			.room(room)
			.build();

	}
}
