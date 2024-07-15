package com.nhnacademy.exam.hotel.domain;

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
@Table(name = "reviews_and_images")
public class ReviewImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_image_id")
	private Long reviewImageId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "review_id")
	private Review review;

	@ManyToOne(optional = false)
	@JoinColumn(name = "image_id")
	private Image image;
}
