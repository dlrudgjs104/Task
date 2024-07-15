package com.nhnacademy.exam.hotel.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "points")
public class Point {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "point_id")
	private Long pointId;

	@Column(name = "point_amount")
	private Long pointAmount;

	@Column(name = "point_created_at")
	private LocalDateTime pointCreatedAt = LocalDateTime.now();

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne
	@JoinColumn(name = "reservation_id")
	private Reservation reservation;

	@OneToOne
	@JoinColumn(name = "review_id")
	private Review review;

}
