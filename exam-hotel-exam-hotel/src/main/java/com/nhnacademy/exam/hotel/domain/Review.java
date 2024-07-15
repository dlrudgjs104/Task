package com.nhnacademy.exam.hotel.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "reivews")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Long reviewId;

	@Column(name = "review_title", insertable = false)
	private String reviewTitle;

	@Column(name = "review_content", insertable = false)
	private String reviewContent;

	@Column(name = "review_created_at", insertable = false)
	private LocalDateTime reviewCreatedAt = LocalDateTime.now();

	@Column(name = "review_check", insertable = false)
	private boolean reviewCheck;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(optional = false)
	@JoinColumn(name = "reservation_id")
	private Reservation reservation;
}
